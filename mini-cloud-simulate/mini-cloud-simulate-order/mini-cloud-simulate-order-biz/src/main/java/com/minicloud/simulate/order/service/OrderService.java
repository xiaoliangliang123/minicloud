package com.minicloud.simulate.order.service;

/**
 * @Author alan.wang
 */
public interface OrderService {

    /**
     * @desc： 模拟生成订单
     * */
    String newOrder() throws Exception;

    long createOrder() throws Exception;

    void statusToSucces(Long orderId);

    void statusToFailed(Long orderId, String reason);

    void createRandomTimeOrderWithoutPay();

    void pushOrderWithoutPayIndelayQuene(Long orderId, Long expireTime);
}
