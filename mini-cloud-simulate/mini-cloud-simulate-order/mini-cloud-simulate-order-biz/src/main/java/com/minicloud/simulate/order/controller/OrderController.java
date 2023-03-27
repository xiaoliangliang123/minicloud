package com.minicloud.simulate.order.controller;

import com.minicloud.common.auth.config.MiniCloudFeignInterface;
import com.minicloud.common.core.util.ResponseX;
import com.minicloud.simulate.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author alan.wang
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * @desc: 模拟生成订单
     * **/
    @PutMapping("/create")
    public ResponseEntity createOrder() throws Exception {

        orderService.newOrder();
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 订单创建demo代码
     * */
    @PostMapping("/mq/create")
    public ResponseX createMQOrder() throws Exception {
        long startTime = System.currentTimeMillis();
        long orderId = orderService.createOrder();
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        return ResponseX.ok("创建订单成功,单号："+orderId+",状态：处理中,耗时："+useTime);
    }

    /**
     *
     * 订单状态变更为创建成功，供feign调用
     * */
    @MiniCloudFeignInterface
    @PostMapping("/status/success")
    public ResponseX statusToSuccess(@RequestParam("orderId") Long orderId) throws Exception {
        orderService.statusToSucces(orderId);
        return ResponseX.ok();
    }

    /**
     * 模拟创建一个状态为创建成功未支付状态的订单
     * */
    @PostMapping("/createOrderWithoutPay")
    public ResponseX createOrderWithoutPay() throws Exception {
        long startTime = System.currentTimeMillis();
        orderService.createRandomTimeOrderWithoutPay();
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        return ResponseX.ok("创建状态为已创建订单成功：,状态：已创建,耗时："+useTime);
    }

    /**
     * 消息添加到delayquene 队列中
     * */
    @PostMapping("/orderWithoutPaydelayQuene")
    public ResponseX orderWithoutPaydelayQuene(@RequestParam("orderId")Long orderId,@RequestParam("expireTime")Long expireTime) throws Exception {
        orderService.pushOrderWithoutPayIndelayQuene(orderId,expireTime);
        return ResponseX.ok("push orderWithoutPay in delayQuene success!");
    }



    /**
     *
     * 订单状态变更为创建失败，供feign调用
     * */
    @MiniCloudFeignInterface
    @PostMapping("/status/failed")
    public ResponseX statusToFailed(@RequestParam("orderId") Long orderId,@RequestParam("reason") String reason) throws Exception {
        orderService.statusToFailed(orderId,reason);
        return ResponseX.ok();
    }
}
