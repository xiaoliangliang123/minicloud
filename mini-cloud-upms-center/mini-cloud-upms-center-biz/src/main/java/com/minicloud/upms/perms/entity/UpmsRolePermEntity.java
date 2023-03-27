package com.minicloud.upms.perms.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * UpmsRolePermEntity: 数据映射实体定义
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
    table = "upms_role_perm"
)
public class UpmsRolePermEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 权限url
   */
  @TableField("perm_id")
  private Integer permId;

  /**
   * 角色id
   */
  @TableField("role_id")
  private Integer roleId;

  /**
   * 租戶Id
   */
  @TableField("tenant_id")
  private Integer tenantId;

  @Override
  public final Class<? extends IEntity> entityClass() {
    return UpmsRolePermEntity.class;
  }

  @Override
  public final UpmsRolePermEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final UpmsRolePermEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
