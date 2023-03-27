package com.minicloud.goods.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(
        chain = true
)
@EqualsAndHashCode(
        callSuper = false
)
@FluentMybatis(
        table = "goods_item_sub"
)
public class GoodsItemSubEntity extends RichEntity {

    @TableId(auto = false)
    private Long orderId;

    @TableId(auto = false)
    private Integer goodsId;


    private Integer quantity;

    @Override
    public Serializable findPk() {
        return this.goodsId;
    }

    @Override
    public final Class<? extends IEntity> entityClass() {
        return GoodsItemSubEntity.class;
    }

    @Override
    public final GoodsItemSubEntity changeTableBelongTo(TableSupplier supplier) {
        return super.changeTableBelongTo(supplier);
    }

    @Override
    public final GoodsItemSubEntity changeTableBelongTo(String table) {
        return super.changeTableBelongTo(table);
    }

}
