package com.minicloud.simulate.order.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * OrderEntity: 数据映射实体定义
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
    table = "order_content"
)
public class OrderEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 订单id
   */
  @TableId(
      value = "order_id",
      auto = false
  )
  private Long orderId;

  /**
   */
  @TableField("create_time")
  private LocalDateTime createTime;

  /**
   * 订单详情
   */
  @TableField("order_detail")
  private String detail;

  /**
   */
  @TableField("reason")
  private String reason;

  /**
   * 0:已创建，处理中，1：成功，2：失败
   */
  @TableField("order_status")
  private Integer status;

  @Override
  public Serializable findPk() {
    return this.orderId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return OrderEntity.class;
  }

  @Override
  public final OrderEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final OrderEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
