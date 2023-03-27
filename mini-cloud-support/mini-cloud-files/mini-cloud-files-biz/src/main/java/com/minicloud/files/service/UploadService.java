package com.minicloud.files.service;


import com.minicloud.files.dto.Chunk;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author alan.wang
 * 分片/断点续传上传文件接口
 * SingleModeUploadService 单体部署-分片/断点续传上传文件实现类
 * ClusterModeUploadService 集群部署-分片/断点续传上传文件实现类
 */
public interface UploadService {


    /**
     * @param chunk 分片
     * @param response 相应流，返回status 201-部分成功,200-完全成功
     * @param bucketName bucket 名称，这里是minio的bucketName
     * @return String 上传成功后minio返回的 id
     * */
    String chunkFileUpload(Chunk chunk, String bucketName, HttpServletResponse response) throws Exception;
}
