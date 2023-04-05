package com.minicloud.order.dubbo.dto;

import lombok.Data;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo.dto
 * @Project：mini-cloud-gateaway-center
 * @name：OrderDTO
 * @Date：2023/4/5 15:17
 * @Filename：OrderDTO
 */
@Data
public class OrderDTO {

    private Integer orderId ;

    private String detail ;
}
