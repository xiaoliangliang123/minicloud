package com.minicloud.simulate.order.cluster.impl;

import com.minicloud.simulate.order.cluster.ClusterServiceInvoker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author alan.wang
 * ClusterServiceInvoker 实现类，实现zookeeper中获取服务实例并发送订单延迟队列
 */
@Service("zookeeperClusterServiceInvoker")
@AllArgsConstructor
public class ZookeeperClusterServiceInvoker implements ClusterServiceInvoker {
    @Override
    public void orderInDelayQuene(String serverName, String version, Long orderId, Integer expireTime) {

    }
}
