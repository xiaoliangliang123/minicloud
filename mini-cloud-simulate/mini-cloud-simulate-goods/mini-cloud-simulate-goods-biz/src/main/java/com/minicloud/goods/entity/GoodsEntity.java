package com.minicloud.goods.entity;

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

/**
 * GoodsEntity: 数据映射实体定义
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
    table = "goods"
)
public class GoodsEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 商品id
   */
  @TableId("goods_id")
  private Integer goodsId;

  /**
   * 商品库存
   */
  @TableField("goods_stock")
  private Integer goodsStock;

  @Override
  public Serializable findPk() {
    return this.goodsId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return GoodsEntity.class;
  }

  @Override
  public final GoodsEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final GoodsEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
