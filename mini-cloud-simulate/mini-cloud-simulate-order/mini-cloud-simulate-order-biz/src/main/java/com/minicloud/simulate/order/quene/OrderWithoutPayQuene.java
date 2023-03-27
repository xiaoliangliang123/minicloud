package com.minicloud.simulate.order.quene;

import java.util.concurrent.TimeUnit;

/**
 * @Author alan.wang
 * 添加订单消息到delayquene 顶层接口
 */
public interface OrderWithoutPayQuene {

    /**
     *  添加消息方法
     * @param orderId 订单id
     * @param expireTime 过期时间
     * @param timeUnit 时间单位
     */
    void push(long orderId, long expireTime, TimeUnit timeUnit);

    /**
     * 启动消息消费方法，执行后会while(true)不断循环执行
     *
     * */
    void take() ;
}
