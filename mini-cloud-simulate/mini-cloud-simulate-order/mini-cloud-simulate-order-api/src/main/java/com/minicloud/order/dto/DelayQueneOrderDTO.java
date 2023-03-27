package com.minicloud.order.dto;

import lombok.Data;

/**
 * @Author alan.wang
 */
@Data
public class DelayQueneOrderDTO {

    private Long orderId;
    private Long expireTime ;
}
