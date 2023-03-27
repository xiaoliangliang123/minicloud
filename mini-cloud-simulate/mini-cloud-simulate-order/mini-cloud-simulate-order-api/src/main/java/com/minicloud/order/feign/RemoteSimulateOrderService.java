package com.minicloud.order.feign;

import com.minicloud.common.constant.MiniCloudCommonConstant;
import com.minicloud.common.core.util.ResponseX;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.FEIGN_REQUEST;
import static com.minicloud.common.constant.MiniCloudCommonConstant.ServerConstant.MINI_CLOUD_SERVER_ORDER;


/**
 * @Author alan.wang
 */
@FeignClient(contextId = "remoteSimulateOrderService",value = MINI_CLOUD_SERVER_ORDER)
public interface RemoteSimulateOrderService {


    /**
     * 订单状态变更为成功
     * */
    @PostMapping("/order/status/success")
    ResponseX statusToSuccess(@RequestParam("orderId") Long orderId,@RequestHeader(FEIGN_REQUEST) String requestHeader);

    /**
     * 订单状态变更为失败
     * */
    @PostMapping("/order/status/failed")
    ResponseX statusToFailed(@RequestParam("orderId")  Long orderId,@RequestParam("reason") String reason,@RequestHeader(FEIGN_REQUEST) String requestHeader);




}
