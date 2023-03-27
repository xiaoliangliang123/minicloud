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
 * UpmsOrgEntity: 数据映射实体定义
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
    table = "upms_org"
)
public class UpmsOrgEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId("org_id")
  private Integer orgId;

  /**
   * 组织机构名称
   */
  @TableField("org_name")
  private String orgName;

  /**
   * 父级组织id
   */
  @TableField("org_parent_Id")
  private Integer orgParentId;

  /**
   * 标签id
   */
  @TableField("org_tag_id")
  private Integer orgTagId;

  /**
   * 租户id
   */
  @TableField("tenant_id")
  private Integer tenantId;

  @Override
  public Serializable findPk() {
    return this.orgId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return UpmsOrgEntity.class;
  }

  @Override
  public final UpmsOrgEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final UpmsOrgEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
