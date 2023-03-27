package com.minicloud.simulate.order.mq;

import lombok.Data;

/**
 * @Author alan.wang
 */
@Data
public class MessageTransationDTO {

    public static final Integer CREATE_ORDER = 1;
    public static final Integer GOODS_STOCK_NOT_ENOUGH = 2;

    private String id;
    private Integer type ;
    private String payload;
}
