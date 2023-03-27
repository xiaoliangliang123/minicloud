package com.minicloud.simulate.order.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SellOrderEntity: 数据映射实体定义
 *
 * @author Powered By Fluent Mybatis
 */
@SuppressWarnings("unchecked")
@Data
@Accessors(
    chain = true
)
@EqualsAndHashCode(
    callSuper = false
)
@FluentMybatis(
    table = "orders"
)
public class OrdersEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 订单id
   */
  @TableId(value = "order_id",auto = false)
  private String orderId;

  /**
   * 订单创建时间
   */
  @TableField(value = "order_create_time")
  private LocalDateTime orderCreateTime;

  @Override
  public Serializable findPk() {
    return this.orderId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return OrdersEntity.class;
  }

  @Override
  public final OrdersEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final OrdersEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
