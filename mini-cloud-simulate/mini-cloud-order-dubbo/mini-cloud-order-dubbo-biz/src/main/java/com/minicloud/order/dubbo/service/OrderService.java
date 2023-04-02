package com.minicloud.order.dubbo.service;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo.service
 * @Project：mini-cloud-gateaway-center
 * @name：OrderService
 * @Date：2023/4/2 16:30
 * @Filename：OrderService
 */
public interface OrderService {
    Integer create(Integer goodsId, Integer stock);
}
