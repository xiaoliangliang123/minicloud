package com.minicloud.goods.dubbo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.goods.dubbo.entity
 * @Project：mini-cloud-gateaway-center
 * @name：GoodsEntity
 * @Date：2023/4/3 19:58
 * @Filename：GoodsEntity
 */
@Data
@TableName("dubbo_goods")
@EqualsAndHashCode(callSuper = true)
public class GoodsEntity extends Model {

    @TableId
    private Integer goodsId;

    @TableField
    private Integer goodsStock;

}
