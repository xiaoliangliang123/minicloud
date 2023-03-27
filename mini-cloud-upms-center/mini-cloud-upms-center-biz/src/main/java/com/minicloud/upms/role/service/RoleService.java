package com.minicloud.upms.role.service;


import com.minicloud.common.core.page.PageReq;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.dto.UserRoleDTO;

import java.util.List;

/**
 * @Author alan.wang
 * @desc : upms role service 类
 */
public interface RoleService {

    /**
     * @param roleDTOS
     * @return 保存后roles ids
     */
    List<Integer> saveRoles(List<RoleDTO> roleDTOS);

    /**
     * @Desc: 保存roles
     * @param upmsUserRoleDTOS
     */
    void saveUserRoles(Integer userId,List<UserRoleDTO> upmsUserRoleDTOS);

    List<RoleDTO> queryRolesByUserId(Integer userId);

    PageReq page(PageReq pageReq,RoleDTO upmsRoleDTO);

    Integer saveOrEdit(RoleDTO upmsRoleDTO);

    RoleDTO getRoleById(String roleId);

    Integer delete(Integer roleId);

    List<RoleDTO> all();

    List<RoleDTO> queryRolesByTenantIdAndUserId(Integer tenantId, Integer userId);
}
