package com.minicloud.files.config;

import cn.hutool.core.util.StrUtil;
import com.minicloud.files.dto.Chunk;
import com.minicloud.files.dto.MinioResponseDTO;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @Author alan.wang
 */
@Component
public class MinioUtil {

    @Autowired
    private MinioClient client;


    @Value("${minio.bucketName}")
    private String bucketName;



    private static final String SEPARATOR_DOT = ".";

    private static final String SEPARATOR_ACROSS = "-";

    private static final String SEPARATOR_STR = "";

    // 存储桶名称
    private static final String chunkBucKet = "img";

    /**
     * 不排序
     */
    public final static boolean NOT_SORT = false;

    /**
     * 排序
     */
    public final static boolean SORT = true;

    /**
     * 默认过期时间(分钟)
     */
    private final static Integer DEFAULT_EXPIRY = 60;

    /**
     * 删除分片
     */
    public final static boolean DELETE_CHUNK_OBJECT = true;
    /**
     * 不删除分片
     */
    public final static boolean NOT_DELETE_CHUNK_OBJECT = false;

    /**
     * @param bucketName
     * @return boolean
     * @Description 判断 bucket是否存在
     * @author exe.wangtaotao
     * @date 2020/10/21 16:33
     */
    public boolean bucketExists(String bucketName) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean objectExits(String bucketName, String globalId, Integer chunkNumber)  {
        try {
            StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(bucketName).object(globalId+"/"+chunkNumber).build();
            StatObjectResponse statObjectResponse = client.statObject(statObjectArgs);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean objectExits(String bucketName, String objname)  {
        try {
            StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(bucketName).object(objname).build();
            StatObjectResponse statObjectResponse = client.statObject(statObjectArgs);

            return true;
        }catch (Exception e){
            return false;
        }


    }

    /**
     * 创建存储桶
     * 创建 bucket
     *
     * @param bucketName
     */
    public void makeBucket(String bucketName) {
        try {
            boolean isExist = bucketExists(bucketName);
            if (!isExist) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> listBucketNames() {
        List<Bucket> bucketList = listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }


    @SneakyThrows
    private List<Bucket> listBuckets() {
        return client.listBuckets();
    }


    /**
     * 获取对象文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象名称前缀(文件夹 /xx/xx/xxx.jpg 中的 /xx/xx/)
     * @return objectNames
     */
    public List<String> listObjectNames(String bucketName, String prefix) {
        return listObjectNames(bucketName, prefix, NOT_SORT);
    }


    /**
     * 获取对象文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象名称前缀(文件夹 /xx/xx/xxx.jpg 中的 /xx/xx/)
     * @param sort       是否排序(升序)
     * @return objectNames
     */
    @SneakyThrows
    public List<String> listObjectNames(String bucketName, String prefix, Boolean sort) {

        boolean flag = bucketExists(bucketName);
        if (flag) {

            ListObjectsArgs listObjectsArgs;
            if (null == prefix) {
                listObjectsArgs = ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .recursive(true)
                        .build();
            } else {
                listObjectsArgs = ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .recursive(true)
                        .build();
            }
            Iterable<Result<Item>> chunks = client.listObjects(listObjectsArgs);
            List<String> chunkPaths = new ArrayList<>();
            for (Result<Item> item : chunks) {
                chunkPaths.add(item.get().objectName());
            }
            if (sort) {
                chunkPaths.sort(new Str2IntComparator(false));
            }
            return chunkPaths;
        }
        return new ArrayList<>();
    }

    /**
     * 在桶下创建文件夹,文件夹层级结构根据参数决定
     *
     * @param bucket 桶名称
     * @param WotDir 格式为 xxx/xxx/xxx/
     */
    @SneakyThrows
    public String createDirectory(String bucket, String WotDir) {
        if (!this.bucketExists(bucket)) {
            return null;
        }
        client.putObject(PutObjectArgs.builder().bucket(bucket).object(WotDir).stream(
                new ByteArrayInputStream(new byte[]{}), 0, -1)
                .build());
        return WotDir;
    }


    /**
     * 删除一个文件
     *
     * @param bucketName
     * @param objectName
     */
    @SneakyThrows
    public boolean removeObject(String bucketName, String objectName) {

        if (!bucketExists(bucketName)) {
            return false;
        }
        client.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
        return true;
    }


    @SneakyThrows
    public List<String> removeObjects(String bucketName, List<String> objectNames) {
        if (!bucketExists(bucketName)) {
            return new ArrayList<>();
        }
        List<DeleteObject> deleteObjects = new ArrayList<>(objectNames.size());
        for (String objectName : objectNames) {
            deleteObjects.add(new DeleteObject(objectName));
        }
        List<String> deleteErrorNames = new ArrayList<>();
        Iterable<Result<DeleteError>> results = client.removeObjects(
                RemoveObjectsArgs.builder()
                        .bucket(bucketName)
                        .objects(deleteObjects)
                        .build());
        for (Result<DeleteError> result : results) {
            DeleteError error = result.get();
            deleteErrorNames.add(error.objectName());
        }
        return deleteErrorNames;
    }


    /**
     * 获取访问对象的外链地址
     * 获取文件的下载url
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiry     过期时间(分钟) 最大为7天 超过7天则默认最大值
     * @return viewUrl
     */
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName, Integer expiry) {
        expiry = expiryHandle(expiry);
        return client.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expiry)
                        .build()
        );
    }


    /**
     * 创建上传文件对象的外链
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @return uploadUrl
     */
    public String createUploadUrl(String bucketName, String objectName) {
        return createUploadUrl(bucketName, objectName, DEFAULT_EXPIRY);
    }

    /**
     * 创建上传文件对象的外链
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @param expiry     过期时间(分钟) 最大为7天 超过7天则默认最大值
     * @return uploadUrl
     */
    @SneakyThrows
    public String createUploadUrl(String bucketName, String objectName, Integer expiry) {
        expiry = expiryHandle(expiry);
        return client.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expiry)
                        .build()
        );
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名
     * @return InputStream
     */
    public void downLoadFile(HttpServletResponse response,String fileName) {
       /* InputStream inputStream = null;
        try {
            StatObjectResponse statObjectResponse = client.statObject(StatObjectArgs.builder().bucket(chunkBucKet).object(fileName).build());
            if (statObjectResponse.size() > 0) {
                inputStream = client.getObject(GetObjectArgs.builder().bucket(chunkBucKet).object(fileName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;*/
        try(InputStream ism = new BufferedInputStream(client.getObject(GetObjectArgs.builder()
                .bucket(chunkBucKet)
                .object(fileName).build()))) {
            // 调用statObject()来判断对象是否存在。
            // 如果不存在, statObject()抛出异常,
            // 否则则代表对象存在
            client.statObject(StatObjectArgs.builder()
                    .bucket(chunkBucKet)
                    .object(fileName).build());
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
            // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，
            // 文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            OutputStream osm = new BufferedOutputStream(response.getOutputStream());
            while ((length = ism.read(buf))>0) {
                osm.write(buf,0, length);
            }
            osm.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public byte[] bytes(String bucketName, String filename) {
        InputStream fileInputStream = null;
        try {
            fileInputStream = client.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename).build());

            return org.apache.commons.io.IOUtils.toByteArray(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 批量下载
     *
     * @param directory
     * @return
     */
    @SneakyThrows
    public List<String> downLoadMore(String bucket, String directory) {
        Iterable<Result<Item>> objs = client.listObjects(ListObjectsArgs.builder().bucket(bucket).prefix(directory).useUrlEncodingType(false).build());
        List<String> list = new ArrayList<>();
        for (Result<Item> result : objs) {
            String objectName = null;
            objectName = result.get().objectName();
            StatObjectResponse statObject = client.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build());
            if (statObject != null && statObject.size() > 0) {
                String fileurl = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucket).object(statObject.object()).method(Method.GET).build());
                list.add(fileurl);
            }
        }
        return list;
    }


    public MinioResponseDTO uploadNormal(File file,String filename, String bucketName, String directory) throws Exception {


        directory = Optional.ofNullable(directory).orElse("");
        String originFilename = Base64.getEncoder().encodeToString(filename.getBytes());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String minFileName = directory + minFileName(file.getName());

        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("originFilename", originFilename);
        userMetadata.put("username", username);
        userMetadata.put("size", file.length()+"");

        InputStream inputStream = new FileInputStream(file);

        //上传文件到指定目录
        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(minFileName)
                .contentType("application/octet-stream")
                .stream(inputStream, inputStream.available(), -1)
                .userMetadata(userMetadata)
                .extraQueryParams(userMetadata)
                .build());
        inputStream.close();


        String url =  getObjectUrl(bucketName, minFileName, DEFAULT_EXPIRY);
        MinioResponseDTO minioResponseDTO =  new MinioResponseDTO(minFileName,originFilename,username,url);
        return minioResponseDTO;
    }


    public MinioResponseDTO upload(MultipartFile multipartFile, String bucketName, String directory) throws Exception {

        InputStream inputStream = multipartFile.getInputStream();
        directory = Optional.ofNullable(directory).orElse("");
        String originFilename = Base64.getEncoder().encodeToString(multipartFile.getOriginalFilename().getBytes());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String minFileName = directory + minFileName(multipartFile.getOriginalFilename());

        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("originFilename", originFilename);
        userMetadata.put("username", username);
        userMetadata.put("size", inputStream.available()+"");

        //上传文件到指定目录
        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(minFileName)
                .contentType(multipartFile.getContentType())
                .stream(inputStream, inputStream.available(), -1)
                .userMetadata(userMetadata)
                .extraQueryParams(userMetadata)
                .build());
        inputStream.close();

        String url =  getObjectUrl(bucketName, minFileName, DEFAULT_EXPIRY);
        MinioResponseDTO minioResponseDTO =  new MinioResponseDTO(minFileName,originFilename,username,url);
        return minioResponseDTO;
    }


    public void download(HttpServletResponse response, String bucketName, String minFileName)  {
        InputStream fileInputStream = null;
        try {
            fileInputStream = client.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(minFileName).build());
            response.setHeader("Content-Disposition", "attachment;filename=" + minFileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 批量创建分片上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param chunkCount 分片数量
     * @return uploadChunkUrls
     */
    public List<String> createUploadChunkUrlList(String bucketName, String objectMD5, Integer chunkCount) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/";
        if (null == chunkCount || 0 == chunkCount) {
            return null;
        }
        List<String> urlList = new ArrayList<>(chunkCount);
        for (int i = 1; i <= chunkCount; i++) {
            String objectName = objectMD5 + i + ".chunk";
            urlList.add(createUploadUrl(bucketName, objectName, DEFAULT_EXPIRY));
        }
        return urlList;
    }

    /**
     * 创建指定序号的分片文件上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param partNumber 分片序号
     * @return uploadChunkUrl
     */
    public String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/" + partNumber + ".chunk";
        return createUploadUrl(bucketName, objectMD5, DEFAULT_EXPIRY);
    }

    public MinioResponseDTO uploadChunk(Chunk chunk) throws Exception {

        InputStream inputStream = chunk.getFile().getInputStream();
        inputStream.mark((chunk.getChunkNumber() -1)*chunk.getChunkNumber() );
        String directory = Optional.ofNullable(chunk.getGlobalId()).orElse("");
        String originFilename = Base64.getEncoder().encodeToString(chunk.getFile().getOriginalFilename().getBytes());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String minFileName = directory + chunk.getGlobalId();
        if(StrUtil.isNotBlank(chunk.getFilename())){
            minFileName = directory+"/"+chunk.getChunkNumber();
        }
        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("originFilename", originFilename);
        userMetadata.put("username", username);
        userMetadata.put("size", inputStream.available()+"");

        //上传文件到指定目录
        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(minFileName)
                .contentType(chunk.getFile().getContentType())
                .stream(inputStream, chunk.getChunkSize(),-1)
                .userMetadata(userMetadata)
                .extraQueryParams(userMetadata)
                .build());
        inputStream.close();


        String url =  getObjectUrl(bucketName, minFileName, DEFAULT_EXPIRY);
        MinioResponseDTO minioResponseDTO =  new MinioResponseDTO(minFileName,originFilename,username,url);
        return minioResponseDTO;
    }

    /**
     * 获取分片文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param ObjectMd5  对象Md5
     * @return objectChunkNames
     */
    public List<String> listChunkObjectNames(String bucketName, String ObjectMd5) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == ObjectMd5) {
            return null;
        }
        return listObjectNames(bucketName, ObjectMd5, SORT);
    }

    /**
     * 获取分片名称地址HashMap key=分片序号 value=分片文件地址
     *
     * @param bucketName 存储桶名称
     * @param ObjectMd5  对象Md5
     * @return objectChunkNameMap
     */
    public Map<Integer, String> mapChunkObjectNames(String bucketName, String ObjectMd5) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == ObjectMd5) {
            return null;
        }
        List<String> chunkPaths = listObjectNames(bucketName, ObjectMd5);
        if (null == chunkPaths || chunkPaths.size() == 0) {
            return null;
        }
        Map<Integer, String> chunkMap = new HashMap<>(chunkPaths.size());
        for (String chunkName : chunkPaths) {
            Integer partNumber = Integer.parseInt(chunkName.substring(chunkName.indexOf("/") + 1, chunkName.lastIndexOf(".")));
            chunkMap.put(partNumber, chunkName);
        }
        return chunkMap;
    }


    /**
     * 合并分片文件成对象文件
     *
     * @param chunkBucKetName   分片文件所在存储桶名称
     * @param composeBucketName 合并后的对象文件存储的存储桶名称
     * @param chunkNames        分片文件名称集合
     * @param objectName        合并后的对象文件名称
     * @return true/false
     */
    @SneakyThrows
    public boolean composeObject(String chunkBucKetName, String composeBucketName, List<String> chunkNames, String objectName, boolean isDeleteChunkObject) {
        if (null == chunkBucKetName) {
            chunkBucKetName = chunkBucKet;
        }
        List<ComposeSource> sourceObjectList = new ArrayList<>(chunkNames.size());
        for (String chunk : chunkNames) {
            sourceObjectList.add(
                    ComposeSource.builder()
                            .bucket(chunkBucKetName)
                            .object(chunk)
                            .build()
            );
        }
        client.composeObject(
                ComposeObjectArgs.builder()
                        .bucket(composeBucketName)
                        .object(objectName)
                        .sources(sourceObjectList)
                        .build()
        );
        if (isDeleteChunkObject) {
            removeObjects(chunkBucKetName, chunkNames);
        }
        return true;
    }

    /**
     * 合并分片文件成对象文件
     *
     * @param bucketName 存储桶名称
     * @param chunkNames 分片文件名称集合
     * @param objectName 合并后的对象文件名称
     * @return true/false
     */
    public boolean composeObject(String bucketName, List<String> chunkNames, String objectName) {
        return composeObject(chunkBucKet, bucketName, chunkNames, objectName, NOT_DELETE_CHUNK_OBJECT);
    }

    /**
     * 合并分片文件成对象文件
     *
     * @param bucketName 存储桶名称
     * @param chunkNames 分片文件名称集合
     * @param objectName 合并后的对象文件名称
     * @return true/false
     */
    public boolean composeObject(String bucketName, List<String> chunkNames, String objectName, boolean isDeleteChunkObject) {
        return composeObject(chunkBucKet, bucketName, chunkNames, objectName, isDeleteChunkObject);
    }

    /**
     * 合并分片文件，合并成功后删除分片文件
     *
     * @param bucketName 存储桶名称
     * @param chunkNames 分片文件名称集合
     * @param objectName 合并后的对象文件名称
     * @return true/false
     */
    public boolean composeObjectAndRemoveChunk(String bucketName, List<String> chunkNames, String objectName) {
        return composeObject(chunkBucKet, bucketName, chunkNames, objectName, DELETE_CHUNK_OBJECT);
    }


    private String minFileName(String originalFileName) {
        String suffix = FilenameUtils.getExtension(originalFileName);
        return UUID.randomUUID().toString().replace(SEPARATOR_ACROSS, SEPARATOR_STR).toUpperCase() +"."+ suffix;
    }


    /**
     * 将分钟数转换为秒数
     *
     * @param expiry 过期时间(分钟数)
     * @return expiry
     */
    private static int expiryHandle(Integer expiry) {
        expiry = expiry * 60;
        if (expiry > 604800) {
            return 604800;
        }
        return expiry;
    }

    public Map metadata(String bucketName ,String filename) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {

        return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(filename).build()).headers().toMultimap();
    }

    public MinioResponseDTO mergeChunk(InputStream inputStream, Chunk chunk) throws Exception {
        String directory = chunk.getGlobalId();
        String originFilename = Base64.getEncoder().encodeToString(chunk.getFilename().getBytes());
        String username = chunk.getUsername();
        String minFileName = directory.concat(".").concat(FilenameUtils.getExtension(chunk.getFilename()));

        Map<String, String> userMetadata = new HashMap<>();
        userMetadata.put("originFilename", originFilename);
        userMetadata.put("username", username);
        userMetadata.put("size", chunk.getTotalSize().toString());


        //上传文件到指定目录
        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(minFileName)
                .contentType("application/octet-stream")
                .stream(inputStream, chunk.getTotalSize(), -1)
                .userMetadata(userMetadata)
                .extraQueryParams(userMetadata)
                .build());
        inputStream.close();


        String url =  getObjectUrl(bucketName, minFileName, DEFAULT_EXPIRY);
        MinioResponseDTO minioResponseDTO =  new MinioResponseDTO(minFileName,originFilename,username,url);
        return minioResponseDTO;
    }

    static class Str2IntComparator implements Comparator<String> {
        private final boolean reverseOrder; // 是否倒序

        public Str2IntComparator(boolean reverseOrder) {
            this.reverseOrder = reverseOrder;
        }

        @Override
        public int compare(String arg0, String arg1) {
            Integer intArg0 = Integer.parseInt(arg0.substring(arg0.indexOf("/") + 1, arg0.lastIndexOf(".")));
            Integer intArg1 = Integer.parseInt(arg1.substring(arg1.indexOf("/") + 1, arg1.lastIndexOf(".")));
            if (reverseOrder) {
                return intArg1 - intArg0;
            } else {
                return intArg0 - intArg1;
            }
        }
    }

}
