package com.minicloud.order.dubbo.service.impl;

import com.minicloud.goods.dubbo.api.RemoteGoodsService;
import com.minicloud.order.dubbo.service.OrderService;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo.service.impl
 * @Project：mini-cloud-gateaway-center
 * @name：OrderServiceImpl
 * @Date：2023/4/2 16:31
 * @Filename：OrderServiceImpl
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Setter
    @DubboReference(version = "1.0.0.0",cluster = "failover",loadbalance = "",interfaceClass = RemoteGoodsService.class)
    private RemoteGoodsService remoteGoodsService;

    @Override
    public Integer create(Integer goodsId, Integer stock) {
        boolean result= remoteGoodsService.subStock(goodsId,stock);
        return 1;
    }
}
