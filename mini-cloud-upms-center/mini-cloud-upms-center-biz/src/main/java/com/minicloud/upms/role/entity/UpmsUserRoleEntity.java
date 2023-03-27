package com.minicloud.upms.role.entity;

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
 * UpmsUserRoleEntity: 数据映射实体定义
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
    table = "upms_user_role"
)
public class UpmsUserRoleEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @TableField(
      value = "user_id")
  private Integer userId;

  /**
   * 角色ID
   */
  @TableField("role_id")
  private Integer roleId;

  /**
   * 租戶ID
   */
  @TableField("tenant_id")
  private Integer tenantId;

  @Override
  public Serializable findPk() {
    return this.userId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return UpmsUserRoleEntity.class;
  }

  @Override
  public final UpmsUserRoleEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final UpmsUserRoleEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
