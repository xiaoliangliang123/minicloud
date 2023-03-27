package com.minicloud.goods.feign;

import com.minicloud.common.constant.MiniCloudCommonConstant;
import com.minicloud.common.core.util.ResponseX;
import com.minicloud.goods.dto.GoodsItemSubDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.FEIGN_REQUEST;
import static com.minicloud.common.constant.MiniCloudCommonConstant.ServerConstant.MINI_CLOUD_SERVER_GOODS;

/**
 * @Author alan.wang
 */
@FeignClient(contextId = "remoteSimulateGoodsService",value = MINI_CLOUD_SERVER_GOODS)
public interface RemoteSimulateGoodsService {

    /**
     *  扣减对应订单的商品的数据库库存
     *  @param orderId 订单id
     *  @param goodsItemSubDTOS 商品集合
     *  @return 成功，报错则同一返回失败
     *
     * */
    @PostMapping("/goods/item/sub/{orderId}")
    ResponseX goodsItemSub(@PathVariable("orderId") Long orderId, @RequestBody List<GoodsItemSubDTO> goodsItemSubDTOS, @RequestHeader(FEIGN_REQUEST) String requestHeader);
}
