package com.minicloud.simulate.order.cluster.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.minicloud.simulate.order.cluster.ClusterServiceInvoker;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author alan.wang
 * ClusterServiceInvoker 实现类，实现nacos中获取服务实例并发送订单延迟队列
 */
@Service("nacosClusterServiceInvoker")
@AllArgsConstructor
public class NacosClusterServiceInvoker implements ClusterServiceInvoker {

    private final DiscoveryClient discoveryClient;

    @Override
    public void orderInDelayQuene(String serverName ,String version, Long orderId, Integer expireTime) {
        List<ServiceInstance> serviceInstances =  discoveryClient.getInstances(serverName);
        serviceInstances.forEach(serviceInstance -> {

            try {

                Map<String,Object> params = new HashMap<>();
                params.put("orderId",orderId);
                params.put("expireTime",expireTime);
                String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/test/orderWithoutPaydelayQuene";
                HttpResponse httpResponse = HttpUtil.createPost(url).form(params).execute();
                String result = httpResponse.body();
                System.out.println(result);
            }catch (Exception e){
                e.printStackTrace();
                //todo 重试代码，demo忽略
            }

        });
    }

}
