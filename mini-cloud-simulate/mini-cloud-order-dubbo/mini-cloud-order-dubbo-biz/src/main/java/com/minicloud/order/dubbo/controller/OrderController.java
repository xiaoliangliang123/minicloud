package com.minicloud.order.dubbo.controller;

import com.minicloud.common.core.util.ResponseX;
import com.minicloud.goods.dubbo.dto.GoodsDTO;
import com.minicloud.order.dubbo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo.controller
 * @Project：mini-cloud-gateaway-center
 * @name：OrderController
 * @Date：2023/4/2 16:25
 * @Filename：OrderController
 */
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    public final OrderService orderService;

    @PostMapping("/create")
    public ResponseX create(Integer goodsId,Integer stock){
        return ResponseX.ok(orderService.create(goodsId,stock));
    }

    @PostMapping("/create/dto")
    public ResponseX createDto(Integer goodsId,Integer stock){

        return ResponseX.ok(orderService.create(goodsId,stock));
    }

    @GetMapping("/check")
    public ResponseX check(){
        return ResponseX.ok("xxx");
    }
}
