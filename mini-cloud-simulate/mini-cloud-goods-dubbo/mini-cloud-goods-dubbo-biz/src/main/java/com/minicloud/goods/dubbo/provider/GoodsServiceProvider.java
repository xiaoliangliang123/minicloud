package com.minicloud.goods.dubbo.provider;

import com.minicloud.goods.dubbo.api.RemoteGoodsService;
import com.minicloud.goods.dubbo.dto.GoodsDTO;
import com.minicloud.goods.dubbo.service.GoodsService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsServiceImpl
 * @Filename：GoodsServiceImpl
 */
@AllArgsConstructor
@DubboService(version = "1.0.0.0",interfaceClass = RemoteGoodsService.class)
public class GoodsServiceProvider implements RemoteGoodsService{

    public final GoodsService goodsService;
    @Override
    public boolean subStock(Integer goodsId,Integer stock) {
        goodsService.subStock(goodsId,stock);
        return true;
    }

    @Override
    public boolean subStock(GoodsDTO goodsDTO) {
        goodsService.subStock(goodsDTO);
        return true;
    }
}
