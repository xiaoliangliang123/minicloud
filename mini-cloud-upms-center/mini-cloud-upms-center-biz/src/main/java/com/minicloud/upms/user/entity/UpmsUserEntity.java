package com.minicloud.upms.user.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * UpmsUserEntity: 数据映射实体定义
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
    table = "upms_user"
)
public class UpmsUserEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 主键ID
   */
  @TableId("user_id")
  private Integer userId;

  /**
   * 可用 0:可用，1：禁用
   */
  @TableField("active")
  private Boolean active;

  /**
   * 已删除 0：未删除 ，1：已删除
   */
  @TableField("deleted")
  private Boolean deleted;

  /**
   * 有效期
   */
  @TableField("expire_time")
  private Date expireTime;

  /**
   * 头像
   */
  @TableField("headimg")
  private String headimg;

  /**
   * 手机号
   */
  @TableField("mobile")
  private String mobile;

  /**
   * 昵称
   */
  @TableField("nickname")
  private String nickname;

  /**
   * 密码
   */
  @TableField("password")
  private String password;

  /**
   * 真实姓名
   */
  @TableField("realname")
  private String realname;

  /**
   * 部门id
   */
  @TableField("dept_id")
  private Integer deptId;


  /**
   * 用户名
   */
  @TableField("username")
  private String username;


  /**
   * 租户id
   */
  @TableField("tenant_id")
  private Integer tenantId;

  @Override
  public Serializable findPk() {
    return this.userId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return UpmsUserEntity.class;
  }

  @Override
  public final UpmsUserEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final UpmsUserEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
