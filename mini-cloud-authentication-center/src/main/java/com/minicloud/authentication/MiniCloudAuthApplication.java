package com.minicloud.authentication;

import com.minicloud.common.cache.EnableMiniCloudRedis;
import com.minicloud.common.fegin.EnableMiniCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @Author alan.wang
 */
@SpringCloudApplication
@EnableMiniCloudFeignClients
@EnableCaching
@EnableAuthorizationServer
@EnableMiniCloudRedis
public class MiniCloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCloudAuthApplication.class, args);
    }
}
