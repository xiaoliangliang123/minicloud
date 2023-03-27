package com.minicloud.simulate.order.cluster;

/**
 * @Author alan.wang
 * 获取指定实例并添加延迟订单消息接口
 */
public interface ClusterServiceInvoker {
    void orderInDelayQuene(String serverName ,String version, Long orderId, Integer expireTime);
}
