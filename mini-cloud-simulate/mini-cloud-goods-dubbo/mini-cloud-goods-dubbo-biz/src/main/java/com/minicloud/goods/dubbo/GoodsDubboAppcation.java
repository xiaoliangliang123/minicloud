package com.minicloud.goods.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsDubboAppcation
 * @Filename：GoodsDubboAppcation
 */
@EnableDubbo
@SpringBootApplication
public class GoodsDubboAppcation {
    public static void main(String[] args) {
        SpringApplication.run(GoodsDubboAppcation.class, args);
    }
}
