package com.minicloud.files;

import com.minicloud.common.auth.annotation.EnableMiniCloudResourceServer;
import com.minicloud.common.fegin.EnableMiniCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author alan.wang
 */
@SpringCloudApplication
@EnableCaching
@EnableMiniCloudFeignClients
@EnableMiniCloudResourceServer
public class MiniCloudFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCloudFilesApplication.class, args);
    }

}
