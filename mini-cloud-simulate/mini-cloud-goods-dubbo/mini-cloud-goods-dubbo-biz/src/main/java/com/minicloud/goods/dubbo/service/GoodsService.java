package com.minicloud.goods.dubbo.service;

import com.minicloud.goods.dubbo.dto.GoodsDTO;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo.service
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsService
 * @Filename：GoodsService
 */
public interface GoodsService {

    void subStock(Integer goodsId,Integer stock);

    void subStock(GoodsDTO goodsDTO);

}
