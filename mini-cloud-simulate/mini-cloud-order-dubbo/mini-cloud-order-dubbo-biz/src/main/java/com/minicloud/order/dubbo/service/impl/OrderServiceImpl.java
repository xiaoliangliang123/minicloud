package com.minicloud.order.dubbo.service.impl;

import cn.hutool.json.JSONUtil;
import com.minicloud.common.core.util.GuidUtil;
import com.minicloud.goods.dubbo.api.RemoteGoodsService;
import com.minicloud.goods.dubbo.dto.GoodsDTO;
import com.minicloud.order.dubbo.entity.OrderEntiry;
import com.minicloud.order.dubbo.mapper.OrderMapper;
import com.minicloud.order.dubbo.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.Setter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @DubboReference(version = "1.0.0.0",timeout = 10000,retries = 3,cluster = "failover",loadbalance = "",interfaceClass = RemoteGoodsService.class)
    private RemoteGoodsService remoteGoodsService;

    @Setter
    @Resource
    private OrderMapper orderMapper;


    @Override
    @GlobalTransactional
    public Integer create(Integer goodsId, Integer stock) {

        GoodsDTO goodsDTO = new GoodsDTO(goodsId,stock);
        boolean result= remoteGoodsService.subStock(goodsDTO);
        if(result){
            OrderEntiry orderEntiry = new OrderEntiry();
            orderEntiry.setOrderId(GuidUtil.longGuid());
            orderEntiry.setOrderDetail(JSONUtil.toJsonStr(goodsDTO));
            orderMapper.insert(orderEntiry);
        }
        throw new RuntimeException("test seata exception");

    }
}
