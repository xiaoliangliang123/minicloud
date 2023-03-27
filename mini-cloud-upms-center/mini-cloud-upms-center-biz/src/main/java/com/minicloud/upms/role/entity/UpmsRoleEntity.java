package com.minicloud.upms.role.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.RefMethod;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import com.minicloud.upms.perms.entity.UpmsRolePermEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * UpmsRoleEntity: 数据映射实体定义
 *
 * @author alan.wang
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
        table = "upms_role"
)
public class UpmsRoleEntity extends RichEntity implements IEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId("role_id")
    private Integer roleId;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色描述
     */
    @TableField("role_desc")
    private String roleDesc;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     *租户id
     */
    @TableField("tenant_id")
    private Integer tenantId;


    @Override
    public Serializable findPk() {
        return this.roleId;
    }

    @Override
    public final Class<? extends IEntity> entityClass() {
        return UpmsRoleEntity.class;
    }

    @Override
    public final UpmsRoleEntity changeTableBelongTo(TableSupplier supplier) {
        return super.changeTableBelongTo(supplier);
    }

    @Override
    public final UpmsRoleEntity changeTableBelongTo(String table) {
        return super.changeTableBelongTo(table);
    }
}
