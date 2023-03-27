package com.minicloud.upms.perms.service.impl;

import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.upms.perms.dao.UpmsPermDao;
import com.minicloud.upms.perms.dto.PermDTO;
import com.minicloud.upms.perms.entity.UpmsPermEntity;
import com.minicloud.upms.perms.mapper.UpmsPermMapper;
import com.minicloud.upms.perms.service.PermService;
import com.minicloud.upms.perms.wrapper.UpmsPermQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 */
@Service
@AllArgsConstructor
public class PermServiceImpl implements PermService {

    private final UpmsPermMapper upmsPermMapper;

    private final UpmsPermDao upmsPermDao;

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public List<Integer> savePerms(Integer roleId, Set<PermDTO> upmsPermDTOS) {

        upmsPermMapper.delete(new UpmsPermQuery().where.roleId().eq(roleId).tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end());
        List<UpmsPermEntity> upmsPermEntities = upmsPermDTOS.stream().map(upmsPermDTO -> {
            UpmsPermEntity upmsPermEntity = new UpmsPermEntity();
            BeanUtils.copyProperties(upmsPermDTO,upmsPermEntity);
            upmsPermEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            upmsPermEntity.setRoleId(roleId);
            return upmsPermEntity;
        }).collect(Collectors.toList());
        upmsPermMapper.insertBatch(upmsPermEntities);
        return upmsPermEntities.stream().map(upmsPermEntity -> upmsPermEntity.getPermId()).collect(Collectors.toList());
    }

    @Override
    public List<PermDTO> queryRolesPermsByRoleIds(Integer[] roleIds) {

        List<UpmsPermEntity> upmsPermEntities =  upmsPermDao.queryRolesPermsByRoleIds(roleIds);
        List<PermDTO> upmsPermDTOS = upmsPermEntities.stream().map(upmsPermEntity -> {
            PermDTO upmsPermDTO =  new PermDTO();
            BeanUtils.copyProperties(upmsPermEntity,upmsPermDTO);
            return upmsPermDTO;
        }).distinct().collect(Collectors.toList());
        return upmsPermDTOS;

    }

    @Override
    public List<PermDTO> queryRolesPermsByTenantIdAndRoleIds(Integer tenantId, Integer[] roleIds) {
        List<UpmsPermEntity> upmsPermEntities =  upmsPermDao.queryRolesPermsByRoleIds(tenantId,roleIds);
        List<PermDTO> upmsPermDTOS = upmsPermEntities.stream().map(upmsPermEntity -> {
            PermDTO upmsPermDTO =  new PermDTO();
            BeanUtils.copyProperties(upmsPermEntity,upmsPermDTO);

            return upmsPermDTO;
        }).collect(Collectors.toList());
        return upmsPermDTOS;
    }

}
