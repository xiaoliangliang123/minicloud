package com.minicloud.goods.service;

import com.minicloud.goods.dto.GoodsItemSubDTO;

import java.util.List;

/**
 * @Author alan.wang
 */
public interface GoodsService {


    boolean subStock(Integer goodsId, Integer num);

    void updateStock();

    void itemSub(Long orderId, List<GoodsItemSubDTO> goodsItemSubDTOS) throws Exception;

}
