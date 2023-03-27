package com.minicloud.upms.perms.service.impl;

import com.minicloud.upms.perms.dto.RolePermDTO;
import com.minicloud.upms.perms.entity.UpmsRolePermEntity;
import com.minicloud.upms.perms.mapper.UpmsRolePermMapper;
import com.minicloud.upms.perms.service.RolePermService;
import com.minicloud.upms.role.dao.UpmsRoleDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 */
@Service
@AllArgsConstructor
public class RolePermServiceImpl implements RolePermService {

    private final UpmsRolePermMapper upmsRolePermMapper;





    @Autowired
    private UpmsRoleDao upmsRoleDao;

    @Override
    public void saveRolePerms(List<RolePermDTO> rolePermDTOS) {

        List<UpmsRolePermEntity> upmsRolePermEntities = rolePermDTOS.parallelStream().map(rolePermDTO -> {

            UpmsRolePermEntity upmsRolePermEntity = new UpmsRolePermEntity();
            BeanUtils.copyProperties(rolePermDTO,upmsRolePermEntity);
            return upmsRolePermEntity;
        }).collect(Collectors.toList());

        upmsRolePermMapper.insertBatch(upmsRolePermEntities);
    }




}
