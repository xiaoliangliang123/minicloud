package com.minicloud.upms;

import com.minicloud.common.auth.annotation.EnableMiniCloudResourceServer;
import com.minicloud.common.fegin.EnableMiniCloudFeignClients;
import com.minicloud.swagger.annotation.EnableMiniCloudSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author alan.wang
 */
@EnableCaching
@SpringCloudApplication
@EnableMiniCloudFeignClients
@EnableMiniCloudResourceServer
@EnableMiniCloudSwagger
public class MiniCloudUpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCloudUpmsApplication.class, args);
    }

}
