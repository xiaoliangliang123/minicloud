package com.minicloud.goods.dubbo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo.dto
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsDTO
 * @Date：2023/4/4 16:48
 * @Filename：GoodsDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO implements Serializable {

    private Integer goodsId;

    private Integer goodsStock;

}
