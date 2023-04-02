package com.minicloud.goods.dubbo.provider;

import com.minicloud.goods.dubbo.api.RemoteGoodsService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsServiceImpl
 * @Filename：GoodsServiceImpl
 */
@DubboService(version = "1.0.0.0",interfaceClass = RemoteGoodsService.class)
public class GoodsServiceProvider implements RemoteGoodsService{

    @Override
    public boolean subStock(Integer goodsId,Integer stock) {
        System.out.println(goodsId+""+stock);
        return true;
    }
}
