package com.minicloud.upms.role.dao;

import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import cn.org.atool.fluent.mybatis.base.crud.JoinBuilder;
import com.minicloud.upms.role.dao.base.UpmsRoleBaseDao;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.wrapper.UpmsRoleQuery;
import com.minicloud.upms.role.wrapper.UpmsUserRoleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author alan.wang
 */
@Repository
public class UpmsRoleDaoImpl extends UpmsRoleBaseDao implements UpmsRoleDao {


    /**
     * @desc: 关联upms_role, upms_user_role表查询出指定用户id 的角色
     */
    @Override
    public List<RoleDTO> queryRolesByUserId(Integer userId) {

        //select role_id,role_name,role_desc,role_code from upms_role role inner join upms_user_role urole on urole.role_id = role.role_id where urole.user_id = #userId
        IQuery iQuery = JoinBuilder.from(new UpmsRoleQuery("role").select.roleId().roleCode().roleName().roleDesc().end()).
                join(new UpmsUserRoleQuery("urole").where.userId().eq(userId).end()).on(l -> l.where.roleId(), r -> r.where.roleId()).endJoin().build();
        return this.mapper.listPoJos(RoleDTO.class, iQuery);
    }

    @Override
    public List<RoleDTO> queryRolesByTenantIdAndUserId(Integer tenantId, Integer userId) {
        IQuery iQuery = JoinBuilder.from(new UpmsRoleQuery("role").select.roleId().roleCode().roleName().roleDesc().end()).
                join(new UpmsUserRoleQuery("urole").where.tenantId().eq(tenantId).userId().eq(userId).end()).on(l -> l.where.roleId(), r -> r.where.roleId()).endJoin().build();
        return this.mapper.listPoJos(RoleDTO.class, iQuery);
    }
}
