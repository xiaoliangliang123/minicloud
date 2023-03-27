package com.minicloud.files.service.impl;


import com.minicloud.files.config.MinioUtil;
import com.minicloud.files.dto.Chunk;
import com.minicloud.files.dto.MinioResponseDTO;
import com.minicloud.files.service.UploadService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author alan.wang
 * 单体部署-分片/断点续传上传文件实现类
 */
public class SingleModeUploadService extends BaseUploadService implements UploadService {




    public SingleModeUploadService(MinioUtil minioUtil) {
        super(minioUtil);
    }

    /**
     * 单体部署时分片文件上传的实现类方法
     * 流程如下：
     * 1.创建uuid 文件夹
     * 2.按照分片的大小，将文件指针移动到指定位置并写入这个分片的内容
     * 3.写成功一个则放入一个chunk 块号码的文件标记
     * 4.当块文件数量等于标记文件数量时，将文件上川岛minio上
     * 5.删除临时文件和临时文件夹以及标记文件,并返回mini 文件保存路径
     */
    @Override
    public String chunkFileUpload(Chunk chunk, String bucketName, HttpServletResponse response) throws Exception {

        //根据上传文件创建临时文件夹
        String tempGlobalDirPath = getTempUploadDirPath() + File.separator + chunk.getGlobalId();
        File tempGlobalDir = new File(tempGlobalDirPath);
        if (!tempGlobalDir.exists()) {
            tempGlobalDir.mkdirs();
        }

        //判断分片标记文件是否已经存在，如果存在则直接返回成功
        File chunkTagFile = new File(getTempUploadDirPath() + File.separator + chunk.getGlobalId() + File.separator + chunk.getChunkNumber());
        if (chunkTagFile.exists()) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return null;
        }

        //保存临时文件
        String filename = getTempUploadDirPath() + chunk.getGlobalId() + "." + FilenameUtils.getExtension(chunk.getFilename());
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");

             FileChannel fileChannel = randomAccessFile.getChannel()) {

            //根据chunk 分片number 计算需要移动的position 指针
            int offset = (chunk.getChunkNumber() - 1) * 1024 * 1024 * 5;
            byte[] bytes = new byte[chunk.getCurrentChunkSize().intValue()];
            IOUtils.readFully(chunk.getFile().getInputStream(), bytes);
            ByteBuffer byteBuffer = ByteBuffer.allocate(chunk.getCurrentChunkSize().intValue());
            byteBuffer.put(bytes);
            byteBuffer.flip();
            fileChannel.write(byteBuffer, offset);
            chunkTagFile.createNewFile();

        }

        //临时文件夹内分片标记文件数量等于分片的总数量
        if (tempGlobalDir.list().length == chunk.getTotalChunks()) {
            String dir = DateTimeFormatter.ofPattern("YYYYMMdd").format(LocalDate.now());
            File tempFile = new File(filename);

            //上传到minio服务器（如果只保存到本地，可以不需要这行）
            MinioResponseDTO minioResponseDTO = getMinioUtil().uploadNormal(tempFile, chunk.getFile().getOriginalFilename(), bucketName, dir);
            response.setStatus(HttpServletResponse.SC_OK);

            //删除临时文件夹内的标记文件
            for (String tf : tempGlobalDir.list()) {
                File tFile = new File(tempGlobalDirPath + File.separator + tf);
                tFile.delete();
            }

            //删除临时文件夹
            tempGlobalDir.delete();

            //删除临时文件
            tempFile.delete();
            return minioResponseDTO.getFilename();
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "";


    }
}
