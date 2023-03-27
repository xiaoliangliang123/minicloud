package com.minicloud.files.config;

import com.minicloud.files.service.UploadService;
import com.minicloud.files.service.impl.ClusterModeUploadService;
import com.minicloud.files.service.impl.SingleModeUploadService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author alan.wang
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * 单点部署方式
     * */
    @Bean
    @ConditionalOnProperty(value = "chunk.upload.mode",havingValue = "single")
    public UploadService singleModeUploadService(MinioUtil minioUtil){
        return new SingleModeUploadService(minioUtil);
    }

    /**
     * 集群部署方式
     * */
    @Bean
    @ConditionalOnProperty(value = "chunk.upload.mode",havingValue = "cluster")
    public UploadService slusterModeUploadService(MinioUtil minioUtil){
        return new ClusterModeUploadService(minioUtil);
    }
}
