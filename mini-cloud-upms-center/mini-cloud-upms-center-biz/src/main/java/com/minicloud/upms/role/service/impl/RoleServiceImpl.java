package com.minicloud.upms.role.service.impl;

import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import cn.org.atool.fluent.mybatis.model.StdPagedList;
import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.common.core.page.PageReq;
import com.minicloud.upms.role.dao.UpmsRoleDao;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.dto.UserRoleDTO;
import com.minicloud.upms.role.entity.UpmsRoleEntity;
import com.minicloud.upms.role.entity.UpmsUserRoleEntity;
import com.minicloud.upms.role.mapper.UpmsRoleMapper;
import com.minicloud.upms.role.mapper.UpmsUserRoleMapper;
import com.minicloud.upms.role.service.RoleService;
import com.minicloud.upms.role.wrapper.UpmsRoleQuery;
import com.minicloud.upms.role.wrapper.UpmsUserRoleQuery;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 * @desc: 用户 角色关联
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private UpmsRoleMapper upmsRoleMapper;

    @Resource
    private UpmsUserRoleMapper upmsUserRoleMapper;

    @Autowired
    private UpmsRoleDao upmsRoleDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<Integer> saveRoles(List<RoleDTO> upmsRoleDTOS) {

        Objects.requireNonNull(upmsRoleDTOS);
        List<UpmsRoleEntity> upmsRoleEntities = upmsRoleDTOS.stream().parallel().map(upmsRoleDTO -> {
            UpmsRoleEntity upmsRoleEntity = new UpmsRoleEntity();
            BeanUtils.copyProperties(upmsRoleDTO, upmsRoleEntity);
            return upmsRoleEntity;
        }).collect(Collectors.toList());
        upmsRoleMapper.insertBatch(upmsRoleEntities);
        return upmsRoleEntities.stream().map(upmsRoleEntity -> upmsRoleEntity.getRoleId()).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveUserRoles(Integer userId, List<UserRoleDTO> upmsUserRoleDTOS) {

        upmsUserRoleMapper.delete(new UpmsUserRoleQuery().where.userId().eq(userId).end());
        Objects.requireNonNull(upmsUserRoleDTOS);
        List<UpmsUserRoleEntity> upmsUserRoleEntities = upmsUserRoleDTOS.stream().map(upmsUserRoleDTO -> {
            UpmsUserRoleEntity upmsUserRoleEntity = new UpmsUserRoleEntity();
            upmsUserRoleEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            BeanUtils.copyProperties(upmsUserRoleDTO, upmsUserRoleEntity);
            return upmsUserRoleEntity;
        }).collect(Collectors.toList());
        upmsUserRoleMapper.insertBatch(upmsUserRoleEntities);
    }

    @Override
    public List<RoleDTO> queryRolesByUserId(Integer userId) {

        List<RoleDTO> roleDTOS = upmsRoleDao.queryRolesByUserId(userId);

        return roleDTOS;

    }

    @Override
    public PageReq page(PageReq pageReq, RoleDTO upmsRoleDTO) {


        StdPagedList<RoleDTO> upmsRoleEntityStdPage = this.upmsRoleMapper.stdPagedPoJo(RoleDTO.class,
                new UpmsRoleQuery()
                        .where.tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end()
                        .limit(pageReq.fromIndex(), pageReq.getSize()));

        pageReq.setTotal(upmsRoleEntityStdPage.getTotal());
        pageReq.setData(upmsRoleEntityStdPage.getData());
        return pageReq;
    }

    @Override
    public Integer saveOrEdit(RoleDTO upmsRoleDTO) {

        //判断角色名称和角色编码不能为空
        Objects.requireNonNull(upmsRoleDTO.getRoleCode(), "role code can not be null");
        Objects.requireNonNull(upmsRoleDTO.getRoleName(), "role name can not be null");

        //编辑操作
        if (Objects.nonNull(upmsRoleDTO.getRoleId())) {

            //判断是否存在upmsRoleDTO role id 对应的数据，不存在则抛异常
            UpmsRoleEntity upmsRoleEntity = this.upmsRoleMapper.findById(upmsRoleDTO.getRoleId());
            Objects.requireNonNull(upmsRoleEntity, "role id :" + upmsRoleDTO.getRoleId() + " is not found");

            //判断是否存在upmsRoleDTO role code 相同并且不是本条id 对应的数据，存在则抛异常
            IQuery iQuery = new UpmsRoleQuery().where.roleCode().eq(upmsRoleDTO.getRoleCode()).roleId().ne(upmsRoleDTO.getRoleId()).tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end();
            upmsRoleEntity = this.upmsRoleMapper.findOne(iQuery);
            if (Objects.nonNull(upmsRoleEntity)) {
                throw new RuntimeException("role code:" + upmsRoleDTO.getRoleCode() + "is exist ");
            }
            upmsRoleEntity = new UpmsRoleEntity();
            BeanUtils.copyProperties(upmsRoleDTO, upmsRoleEntity);
            upmsRoleEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            this.upmsRoleMapper.updateById(upmsRoleEntity);
        }

        //保存操作
        else {

            //判断是否存在upmsRoleDTO role code 对应的数据，存在则抛异常
            IQuery iQuery = new UpmsRoleQuery().where.roleCode().eq(upmsRoleDTO.getRoleCode()).tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end();
            UpmsRoleEntity upmsRoleEntity = this.upmsRoleMapper.findOne(iQuery);
            if (Objects.nonNull(upmsRoleEntity)) {
                throw new RuntimeException("role code:" + upmsRoleDTO.getRoleCode() + "is exist ");
            }
            upmsRoleEntity = new UpmsRoleEntity();
            BeanUtils.copyProperties(upmsRoleDTO, upmsRoleEntity);
            upmsRoleEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            this.upmsRoleMapper.insert(upmsRoleEntity);
            upmsRoleDTO.setRoleId(upmsRoleEntity.getRoleId());
        }
        return upmsRoleDTO.getRoleId();
    }

    @Override
    public RoleDTO getRoleById(String roleId) {
        UpmsRoleEntity upmsRoleEntity = this.upmsRoleMapper.findById(roleId);
        Objects.requireNonNull(upmsRoleEntity, "role id:" + roleId + " can not be found");
        RoleDTO upmsRoleDTO = new RoleDTO();
        BeanUtils.copyProperties(upmsRoleEntity, upmsRoleDTO);
        return upmsRoleDTO;
    }

    @Override
    public Integer delete(Integer roleId) {
        this.upmsRoleMapper.deleteById(roleId);
        return roleId;
    }

    @Override
    public List<RoleDTO> all() {
        return this.upmsRoleMapper.listPoJos(RoleDTO.class, new UpmsRoleQuery()
                .where.tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end());
    }

    @Override
    public List<RoleDTO> queryRolesByTenantIdAndUserId(Integer tenantId, Integer userId) {
        List<RoleDTO> upmsRoleDTOS = upmsRoleDao.queryRolesByTenantIdAndUserId(tenantId, userId);

        return upmsRoleDTOS;
    }


}
