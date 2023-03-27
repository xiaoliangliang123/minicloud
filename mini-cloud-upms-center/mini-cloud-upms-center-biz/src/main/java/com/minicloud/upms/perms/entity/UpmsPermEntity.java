package com.minicloud.upms.perms.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.NotField;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * UpmsPermEntity: 数据映射实体定义
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
    table = "upms_perm"
)
public class UpmsPermEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 权限描述
   */
  @TableId("perm_id")
  private Integer permId;

  /**
   * 权限描述
   */
  @TableField("perm_desc")
  private String permDesc;

  /**
   * 权限名称
   */
  @TableField("perm_name")
  private String permName;

  /**
   * 菜单名称
   */
  @TableField("name")
  private String name;

  /**
   * 菜单路由
   */
  @TableField("path")
  private String path;

  /**
   * 权限url
   */
  @TableField("perm_url")
  private String permUrl;

  /**
   * 权限方法
   */
  @TableField("perm_method")
  private String permMethod;


  /**
   * 所属服务
   */
  @TableField("perm_server")
  private String permServer;


  /**
   * 角色Id
   */
  @TableField("perm_role_id")
  private Integer roleId;

  /**
   * 租戶Id
   */
  @TableField("tenant_id")
  private Integer tenantId;

  @Override
  public final Class<? extends IEntity> entityClass() {
    return UpmsPermEntity.class;
  }

  @Override
  public final UpmsPermEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final UpmsPermEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
