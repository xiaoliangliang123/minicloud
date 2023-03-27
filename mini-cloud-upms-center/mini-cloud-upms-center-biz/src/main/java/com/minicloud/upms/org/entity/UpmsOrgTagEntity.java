package com.minicloud.upms.org.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * UpmsOrgTagEntity: 数据映射实体定义
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
    table = "upms_org_tag"
)
public class UpmsOrgTagEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 组织标签id
   */
  @TableId("org_tag_id")
  private Integer orgTagId;

  /**
   * 组织标签名称
   */
  @TableField("org_tag_name")
  private String orgTagName;

  @Override
  public Serializable findPk() {
    return this.orgTagId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return UpmsOrgTagEntity.class;
  }

  @Override
  public final UpmsOrgTagEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final UpmsOrgTagEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
