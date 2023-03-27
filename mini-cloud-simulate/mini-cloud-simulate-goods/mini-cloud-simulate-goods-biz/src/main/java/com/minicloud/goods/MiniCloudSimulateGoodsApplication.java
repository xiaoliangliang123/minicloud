package com.minicloud.goods;

import com.minicloud.common.auth.annotation.EnableMiniCloudResourceServer;
import com.minicloud.common.cache.EnableMiniCloudRedis;
import com.minicloud.common.fegin.EnableMiniCloudFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author alan.wang
 */
@EnableMiniCloudRedis
@SpringCloudApplication
@EnableCaching
@EnableMiniCloudFeignClients
@EnableMiniCloudResourceServer
public class MiniCloudSimulateGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCloudSimulateGoodsApplication.class, args);
    }

}
