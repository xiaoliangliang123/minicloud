package com.minicloud.upms.perms.dao;

import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.upms.perms.dao.base.UpmsPermBaseDao;
import com.minicloud.upms.perms.entity.UpmsPermEntity;
import com.minicloud.upms.perms.wrapper.UpmsPermQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UpmsPermDaoImpl: 数据操作接口实现
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
@Repository
public class UpmsPermDaoImpl extends UpmsPermBaseDao implements UpmsPermDao {


    @Override
    public List<UpmsPermEntity> queryRolesPermsByRoleIds(Integer[] roleIds) {

        //select .perm.perm.perm_id,perm.perm_name ,perm.perm_url ,perm.perm_server from upms_perm perm inner join upms_role_perm rperm on rperm.perm_id = perm.perm_id and role_id in (roleIds);
//        IQuery iQuery = JoinBuilder.from(new UpmsPermQuery("perm").select.permId().permName().permUrl().permMethod().end()).
//                join(new UpmsRolePermQuery("rperm").select.roleId().end().where.roleId().in(roleIds).end()).on(l->l.where.permId(),r->r.where.permId()).endJoin().build();

        //select
        IQuery iQuery = new UpmsPermQuery().select.permId().name().path().end().where.roleId().in(roleIds).tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end();
        return this.mapper.listEntity(iQuery);
    }

    @Override
    public List<UpmsPermEntity> queryRolesPermsByRoleIds(Integer tenantId, Integer[] roleIds) {
        IQuery iQuery = new UpmsPermQuery().select.permId().name().path().end().where.tenantId().eq(tenantId).roleId().in(roleIds).end();
        return this.mapper.listEntity(iQuery);
    }
}
