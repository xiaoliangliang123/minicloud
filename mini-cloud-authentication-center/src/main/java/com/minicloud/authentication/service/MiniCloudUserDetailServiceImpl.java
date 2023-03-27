package com.minicloud.authentication.service;

import com.minicloud.common.auth.config.MiniCloudAuthenticationUtil;
import com.minicloud.common.auth.model.MiniCloudGrantedAuthority;
import com.minicloud.common.auth.model.MiniCloudUserDetails;
import com.minicloud.upms.perms.dto.PermDTO;
import com.minicloud.upms.user.dto.UserDTO;
import com.minicloud.upms.user.fegin.RemoteUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.minicloud.common.constant.MiniCloudCommonConstant.CommonConstant.FEIGN;


/**
 * @Author alan.wang
 * @desc: UserDetailsService 实现类，实现自定义userdetails 查询接口
 */
public class MiniCloudUserDetailServiceImpl implements UserDetailsService {

    @Resource
    private RemoteUserService remoteUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //通过upms 服务获取用户基本信息，角色以及权限信息
        UserDTO userDTO = remoteUserService.queryUpmsUserByTenantIdAndUsername(MiniCloudAuthenticationUtil.currentTenantId(),username,FEIGN);
        Set<PermDTO>  permDTOSet = new HashSet<>();
        permDTOSet.addAll(userDTO.getPermDTOS());

        //调整为自定义的GrantedAuthority
        List<MiniCloudGrantedAuthority> miniCloudGrantedAuthorities = permDTOSet.stream().map(upmsPermDTO -> {
            MiniCloudGrantedAuthority miniCloudGrantedAuthority = new MiniCloudGrantedAuthority(upmsPermDTO.getName(),upmsPermDTO.getPath());
            return miniCloudGrantedAuthority;
        }).collect(Collectors.toList());

        List<String> roleCodes = userDTO.getUserRoles().stream().map(upmsRoleDTO -> upmsRoleDTO.getRoleCode()).distinct().collect(Collectors.toList());

        //自定义MiniCloudUserDetails 保存进redisTokenStore
        MiniCloudUserDetails userDetails = new MiniCloudUserDetails(userDTO.getUserId(),userDTO.getUsername(), userDTO.getPassword(),roleCodes,miniCloudGrantedAuthorities ,userDTO.getTenantId());
        return userDetails;
    }
}
