package com.minicloud.order.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo
 * @Project：mini-cloud-gateaway-center
 * @name：OrderDubboAppcation
 * @Date：2023/4/2 16:23
 * @Filename：OrderDubboAppcation
 */
//@EnableDubbo
@SpringBootApplication
public class OrderDubboAppcation {
    public static void main(String[] args) {
        SpringApplication.run(OrderDubboAppcation.class, args);
    }
}
