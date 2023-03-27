package com.minicloud.upms.user.service.impl;

import cn.org.atool.fluent.mybatis.model.StdPagedList;
import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.common.auth.model.MiniCloudUserDetails;
import com.minicloud.common.core.page.PageReq;
import com.minicloud.upms.perms.dto.PermDTO;
import com.minicloud.upms.perms.service.PermService;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.dto.UserRoleDTO;
import com.minicloud.upms.role.service.RoleService;
import com.minicloud.upms.user.dao.UpmsUserDao;
import com.minicloud.upms.user.dto.UserDTO;
import com.minicloud.upms.user.entity.UpmsUserEntity;
import com.minicloud.upms.user.mapper.UpmsUserMapper;
import com.minicloud.upms.user.service.UserService;
import com.minicloud.upms.user.wrapper.UpmsUserQuery;
import com.minicloud.upms.user.wrapper.UpmsUserUpdate;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 * @desc: 用户相关操作service
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UpmsUserMapper upmsUserMapper;

    private final RoleService upmsRoleService;

    private final UpmsUserDao upmsUserDao;

    private final PermService upmsPermService;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Integer saveOrEditUser(UserDTO userDTO) {

        if(Objects.isNull(userDTO.getUserId())) {
            UpmsUserEntity upmsUserEntity = new UpmsUserEntity();
            BeanUtils.copyProperties(userDTO, upmsUserEntity);
            upmsUserEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            upmsUserEntity.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode("123456"));
            upmsUserMapper.insert(upmsUserEntity);
            userDTO.setUserId(upmsUserEntity.getUserId());
            upmsRoleService.saveUserRoles(userDTO.getUserId(),userDTO.userRoleDTOS());
        }else {
            UpmsUserEntity upmsUserEntity = new UpmsUserEntity();
            BeanUtils.copyProperties(userDTO, upmsUserEntity);
            upmsUserEntity.setTenantId(MiniCloudAuthenticationUtil.getUserDetails().getTenantId());
            upmsUserMapper.updateById(upmsUserEntity);
            userDTO.setUserId(upmsUserEntity.getUserId());
            upmsRoleService.saveUserRoles(userDTO.getUserId(),userDTO.userRoleDTOS());
        }
        return userDTO.getUserId();

    }

    @Override
    public UserDTO findUserById(Integer userId) {

        Objects.requireNonNull(userId, "userId can not be null");
        UpmsUserEntity upmsUserEntity = upmsUserMapper.findById(userId);

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(upmsUserEntity, userDTO);
        List<RoleDTO> roleDTOS = upmsRoleService.queryRolesByUserId(userId);
        List<PermDTO> permDTOS = wrapperUpmsRoleDTOs(roleDTOS);
        userDTO.setUserRoles(roleDTOS);
        userDTO.setPermDTOS(permDTOS);
        return userDTO;
    }

    private List<PermDTO> wrapperUpmsRoleDTOs(List<RoleDTO> upmsRoleDTOS) {

        List<PermDTO> upmsPermDTOS = upmsPermService.queryRolesPermsByRoleIds(upmsRoleDTOS.stream().map(upmsRoleDTO -> upmsRoleDTO.getRoleId()).toArray(Integer[]::new));
        return upmsPermDTOS;
    }


    @Override
    public UserDTO findByUsername(String username) {

        Objects.requireNonNull(username, "username can not be null");
        UpmsUserEntity upmsUserEntity = upmsUserDao.findByName(username);
        return findUserById(upmsUserEntity.getUserId());
    }

    @Override
    public UserDTO findByTenantIdAndUsername(Integer tenantId, String username) {
        Objects.requireNonNull(username, "username can not be null");
        UpmsUserEntity upmsUserEntity = upmsUserDao.findByTenantIdAndUsername(tenantId,username);
        return findUserByTenantIdAndUserId(tenantId,upmsUserEntity.getUserId());
    }

    @Override
    public boolean deleteByUserId(Integer userId) {
        Integer updated = upmsUserMapper.updateBy(new UpmsUserUpdate().set.deleted().is(true).end().where.userId().eq(userId).end());
        return updated!=0;
    }

    private UserDTO findUserByTenantIdAndUserId(Integer tenantId, Integer userId) {

        Objects.requireNonNull(userId, "userId can not be null");
        UpmsUserEntity upmsUserEntity = upmsUserMapper.findById(userId);

        UserDTO upmsUserDTO = new UserDTO();
        BeanUtils.copyProperties(upmsUserEntity, upmsUserDTO);
        List<RoleDTO> upmsRoleDTOS = upmsRoleService.queryRolesByTenantIdAndUserId(tenantId,userId);
        List<PermDTO> upmsPermDTOS = wrapperUpmsRoleDTOs(tenantId,upmsRoleDTOS);
        upmsUserDTO.setUserRoles(upmsRoleDTOS);
        upmsUserDTO.setPermDTOS(upmsPermDTOS);
        return upmsUserDTO;
    }

    private List<PermDTO> wrapperUpmsRoleDTOs(Integer tenantId, List<RoleDTO> upmsRoleDTOS) {
        List<PermDTO> upmsPermDTOS = upmsPermService.queryRolesPermsByTenantIdAndRoleIds(tenantId,upmsRoleDTOS.stream().map(upmsRoleDTO -> upmsRoleDTO.getRoleId()).toArray(Integer[]::new));
        return upmsPermDTOS;
    }

    @Override
    public List<UserDTO> queryAllUsers() {

        MiniCloudUserDetails miniCloudUserDetails = (MiniCloudUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long startTime = System.currentTimeMillis();
        List<UpmsUserEntity> userEntityList = upmsUserDao.queryAllUsers();
        long endTime = System.currentTimeMillis();
        if(Objects.isNull(userEntityList)){
            return Collections.EMPTY_LIST;
        }
        long startTimeu = System.currentTimeMillis();
        List<UserDTO> upmsUserDTOS = userEntityList.stream().map(upmsUserEntity -> {
            UserDTO upmsUserDTO = new UserDTO();
            BeanUtils.copyProperties(upmsUserEntity,upmsUserDTO);
            return upmsUserDTO;
        }).collect(Collectors.toList());
        long endTimeu = System.currentTimeMillis();
        System.out.println(":==============="+(endTime - startTime)+":"+(endTimeu - startTimeu));
        return upmsUserDTOS;
    }

    @Override
    public PageReq page(PageReq pageReq, RoleDTO upmsRoleDTO) {
        StdPagedList<UserDTO> upmsRoleEntityStdPage = this.upmsUserMapper.stdPagedPoJo(UserDTO.class,new UpmsUserQuery()
                .where.deleted().eq(false).tenantId().eq(MiniCloudAuthenticationUtil.getUserDetails().getTenantId()).end()
                .limit(pageReq.fromIndex(), pageReq.getSize()));
        pageReq.setData(upmsRoleEntityStdPage.getData());
        pageReq.setTotal(upmsRoleEntityStdPage.getTotal());
        return pageReq;
    }


}
