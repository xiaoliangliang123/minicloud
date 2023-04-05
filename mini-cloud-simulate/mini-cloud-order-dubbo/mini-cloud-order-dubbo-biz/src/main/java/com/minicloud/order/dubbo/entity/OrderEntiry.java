package com.minicloud.order.dubbo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.order.dubbo.entity
 * @Project：mini-cloud-gateaway-center
 * @name：OrderEntiry
 * @Date：2023/4/5 16:23
 * @Filename：OrderEntiry
 */
@Data
@TableName("dubbo_order")
@EqualsAndHashCode(callSuper = true)
public class OrderEntiry extends Model {

    @TableId
    private Long orderId;

    @TableField
    private String orderDetail;
}
