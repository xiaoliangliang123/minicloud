package com.minicloud.goods.controller;

import com.minicloud.common.auth.config.MiniCloudFeignInterface;
import com.minicloud.common.core.util.ResponseX;
import com.minicloud.goods.dto.GoodsItemSubDTO;
import com.minicloud.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;



    /**
     * @desc: 模拟扣减商品库存
     * @param goodsId 商品id
     * @param num 商品数量
     *
     * */
    @PostMapping("/subStock/{goodsId}/{num}")
    public boolean subStock(@PathVariable("goodsId")Integer goodsId,@PathVariable("num")Integer num){

        boolean result = goodsService.subStock(goodsId,num);
        if(!result){
            return false;
        }else {
            return true;
        }
    }

    /**
     *
     * 商品初始化数据库数量和缓存数量
     * */
    @PostMapping("/reset/stock")
    public ResponseX updateStock(){
        goodsService.updateStock();
        return ResponseX.ok("库存更新成功");
    }


    /**
     *
     * 扣减对应订单的商品的数据库库存
     * @param orderId 订单id
     * @param goodsItemSubDTOS 商品集合
     * @return 成功，报错则同一返回失败
     * */
    @MiniCloudFeignInterface
    @PostMapping("/item/sub/{orderId}")
    public ResponseX itemSub(@PathVariable("orderId")Long orderId,@RequestBody List<GoodsItemSubDTO> goodsItemSubDTOS) throws Exception {
        goodsService.itemSub(orderId,goodsItemSubDTOS);
        return ResponseX.ok();
    }

}
