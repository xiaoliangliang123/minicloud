package com.minicloud.upms.perms.service;

import com.minicloud.upms.perms.dto.RolePermDTO;

import java.util.List;

/**
 * @Author alan.wang
 */
public interface RolePermService {
    void saveRolePerms(List<RolePermDTO> rolePermDTOS);



}
