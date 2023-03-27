package com.minicloud.goods.dto;

import lombok.Data;
import org.omg.CORBA.PUBLIC_MEMBER;

@Data
public class GoodsItemSubDTO {

    private Long orderId;
    private Integer goodsId;
    private Integer quantity ;

    public GoodsItemSubDTO(){}

    public GoodsItemSubDTO(Long orderId,Integer goodsId,Integer quantity){
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
