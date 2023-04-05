package com.minicloud.goods.dubbo.api;

import com.minicloud.goods.dubbo.dto.GoodsDTO;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo
 * @Project：mini-cloud-gateaway-center
 * @name：RemoteGoodsService
 * @Filename：RemoteGoodsService
 */
public interface RemoteGoodsService {

    boolean subStock(Integer goodsId,Integer stock);

    boolean subStock(GoodsDTO goodsDTO);
}
