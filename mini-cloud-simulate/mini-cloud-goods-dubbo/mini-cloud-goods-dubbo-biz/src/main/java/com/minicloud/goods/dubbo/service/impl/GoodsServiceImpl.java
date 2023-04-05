package com.minicloud.goods.dubbo.service.impl;

import com.minicloud.goods.dubbo.dto.GoodsDTO;
import com.minicloud.goods.dubbo.entity.GoodsEntity;
import com.minicloud.goods.dubbo.mapper.GoodsMapper;
import com.minicloud.goods.dubbo.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo.service.impl
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsServiceImpl
 * @Filename：GoodsServiceImpl
 */
@Service
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    public void subStock(Integer goodsId,Integer stock){

        GoodsEntity goodsEntity = goodsMapper.selectById(goodsId);
        System.out.println(goodsEntity.toString());
    }

    @Override
    public void subStock(GoodsDTO goodsDTO) {

        goodsMapper.subStock(goodsDTO);

        System.out.println(goodsDTO);
    }
}
