package com.minicloud.simulate.order;

import com.minicloud.common.auth.annotation.EnableMiniCloudResourceServer;
import com.minicloud.common.cache.EnableMiniCloudRedis;
import com.minicloud.common.fegin.EnableMiniCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author alan.wang
 * @date: 2022-01-27 13:55
 */
@EnableMiniCloudRedis
@SpringCloudApplication
@EnableCaching
@EnableMiniCloudFeignClients
@EnableMiniCloudResourceServer
public class MiniCloudSimulateOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCloudSimulateOrderApplication.class, args);
    }

}
