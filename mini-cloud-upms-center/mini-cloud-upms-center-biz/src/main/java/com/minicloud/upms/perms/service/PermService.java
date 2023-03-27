package com.minicloud.upms.perms.service;


import com.minicloud.upms.perms.dto.PermDTO;

import java.util.List;
import java.util.Set;

/**
 * @Author alan.wang
 */
public interface PermService {
    List<Integer> savePerms(Integer roleId, Set<PermDTO> permDTOS);

    List<PermDTO> queryRolesPermsByRoleIds(Integer[] roleIds);

    List<PermDTO> queryRolesPermsByTenantIdAndRoleIds(Integer tenantId, Integer[] toArray);

}
