package com.minicloud.files.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.minicloud.files.config.MinioUtil;
import com.minicloud.files.dto.Chunk;
import com.minicloud.files.dto.MinioResponseDTO;
import com.minicloud.files.service.UploadService;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.function.Predicate;

/**
 * @Author alan.wang
 * 集群部署方式实现类
 */

public class ClusterModeUploadService extends BaseUploadService implements UploadService {


    public ClusterModeUploadService(MinioUtil minioUtil) {
        super(minioUtil);
    }

    /**
     * 集群部署时分片文件上传的实现类方法
     */
    @Override
    public String chunkFileUpload(Chunk chunk, String bucketName, HttpServletResponse response) throws Exception {


        //检测是否分片已经合并并上传完成
        Predicate checkMainFileUploadCompleted = o->checkMainFileUploadCompleted(bucketName,chunk);

        //检测是否分片上传完成
        Predicate checkAllChunksUploadCompleted = o->checkAllChunksUploadCompleted(bucketName,chunk);

        //检测某资源是否已经存在
        Predicate objectExist = o->getMinioUtil().objectExits(bucketName, chunk.getGlobalId(), chunk.getChunkNumber());

        //如果全部上传成功，则返回status 200
        if(checkMainFileUploadCompleted.test(true)){
            response.setStatus(HttpServletResponse.SC_OK);
            return getGlobalName(chunk);
        }

        //如果本分片上传成功，先合并分片，返回status 200
        if(checkAllChunksUploadCompleted.test(true)){
            response.setStatus(HttpServletResponse.SC_OK);
            return mergeChunksAndUpload(bucketName,chunk);
        }

        //如果资源已经存在则直接返回上传完毕
        if (objectExist.test(true)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return null;
        }

        //上传分片
        MinioResponseDTO minioResponseDTO = getMinioUtil().uploadChunk(chunk);

        //判断上传后是否分片已全部上传成功
        if (checkAllChunksUploadCompleted.test(true)) {

            //合并分片并上传
            String uploadId = mergeChunksAndUpload(bucketName,chunk);
            response.setStatus(HttpServletResponse.SC_OK);
            return uploadId;
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return minioResponseDTO.getFilename();
        }
    }

    private String mergeChunksAndUpload(String bucketName, Chunk chunk) throws Exception {
        List<String> chunkFilesNames = getMinioUtil().listObjectNames(bucketName, chunk.getGlobalId());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //将所有分片数据取出写入byteArrayOutputStream
        Iterator<String> chunkFilesNamesIterator =  chunkFilesNames.iterator();
        while (chunkFilesNamesIterator.hasNext()) {
            String chunkFilesName =chunkFilesNamesIterator.next();
            byte[] bytes = getMinioUtil().bytes(bucketName, chunkFilesName);
            byteArrayOutputStream.write(bytes);
        }

        MinioResponseDTO minioResponseDTO = getMinioUtil().mergeChunk(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()),chunk);

        //删除分片
        getMinioUtil().removeObjects(bucketName,chunkFilesNames);

        //删除prefix目录
        getMinioUtil().removeObject(bucketName,chunk.getGlobalId());
        return minioResponseDTO.getFilename();
    }
    private boolean checkAllChunksUploadCompleted(String bucketName, Chunk chunk) {
        List<String> chunkFilesNames = getMinioUtil().listObjectNames(bucketName, chunk.getGlobalId());
        return (CollectionUtil.isNotEmpty(chunkFilesNames) && chunkFilesNames.size() == chunk.getTotalChunks());
    }

    private boolean checkMainFileUploadCompleted(String bucketName, Chunk chunk) {
        return getMinioUtil().objectExits(bucketName,getGlobalName(chunk));
    }

    private String getGlobalName(Chunk chunk){
        return chunk.getGlobalId().concat(".").concat(FilenameUtils.getExtension(chunk.getFilename()));
    }
}
