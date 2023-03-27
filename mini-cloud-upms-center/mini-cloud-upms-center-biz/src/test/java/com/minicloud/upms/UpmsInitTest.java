package com.minicloud.upms;

import com.minicloud.upms.perms.dto.RolePermDTO;
import com.minicloud.upms.perms.service.PermService;
import com.minicloud.upms.perms.service.RolePermService;
import com.minicloud.upms.role.service.RoleService;
import com.minicloud.upms.user.entity.UpmsUserEntity;
import com.minicloud.upms.user.mapper.UpmsUserMapper;
import com.minicloud.upms.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author alan.wang
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniCloudUpmsApplication.class)
@Slf4j
public class UpmsInitTest {

    @Autowired
    private UserService upmsUserService;

    @Autowired
    private RoleService upmsRoleService;

    @Autowired
    private PermService upmsPermService;

    @Autowired
    private RolePermService upmsRolePermService;

    @Resource
    private UpmsUserMapper upmsUserMapper;


    /**
     *
     * 初始化角色
     * */
    @Test
    public void testInitRoles() throws InterruptedException {
//        UpmsRoleDTO upmsRoleDTO1 = UpmsRoleDTO.builder().roleName("超级管理员").roleCode("SUPER_ADMIN").roleDesc("最大权限").build();
//        UpmsRoleDTO upmsRoleDTO2 = UpmsRoleDTO.builder().roleName("普通用户1").roleCode("USER1").roleDesc("普通用户1").build();
//        UpmsRoleDTO upmsRoleDTO3 = UpmsRoleDTO.builder().roleName("普通用户2").roleCode("USER1").roleDesc("普通用户2").build();
//
//        List<UpmsRoleDTO> upmsRoleDTOS= Stream.of(upmsRoleDTO1,upmsRoleDTO2,upmsRoleDTO3).collect(Collectors.toList());
//        List<Integer> upmsRolesIds = upmsRoleService.saveRoles(upmsRoleDTOS);
//        log.info("init roles successful:{} ",upmsRolesIds.toArray().toString());
//        testInitUsers(upmsRolesIds);
//        testInitPerms(upmsRolesIds);
    }

    /**
     * 初始化接口权限列表
     * */
    public void testInitPerms(List<Integer> rolesIds){



    }


    /**
     * 初始化角色权限关联表
     * */
    public void testInitRolePerms(Integer roleId,List<Integer> permsId){

        List<RolePermDTO> rolePermDTOS = new ArrayList<>();
        permsId.stream().forEach(pid->{
            rolePermDTOS.add(RolePermDTO.builder().roleId(roleId).permId(pid).build());
        });
        upmsRolePermService.saveRolePerms(rolePermDTOS);
    }


    /**
     *
     * 初始化用户
     * */

    public void testInitUsers(List<Integer> upmsRolesIds) throws InterruptedException {

//        UpmsUserDTO upmsUserDTO1 = UpmsUserDTO.builder().username("admin").password("{bcrypt}" + new BCryptPasswordEncoder().encode("admin")).upmsRoleDTOS(Stream.of(UpmsRoleDTO.builder().roleId(upmsRolesIds.get(0)).build()).collect(Collectors.toList())).build();
//        UpmsUserDTO upmsUserDTO2 = UpmsUserDTO.builder().username("user3").password("{bcrypt}" + new BCryptPasswordEncoder().encode("123")).upmsRoleDTOS(Stream.of(UpmsRoleDTO.builder().roleId(upmsRolesIds.get(1)).build()).collect(Collectors.toList())).build();
//        UpmsUserDTO upmsUserDTO3 = UpmsUserDTO.builder().username("user4").password("{bcrypt}" + new BCryptPasswordEncoder().encode("123")).upmsRoleDTOS(Stream.of(UpmsRoleDTO.builder().roleId(upmsRolesIds.get(2)).build()).collect(Collectors.toList())).build();
//
//        upmsUserService.saveUser(upmsUserDTO1);
//        upmsUserService.saveUser(upmsUserDTO2);
//        upmsUserService.saveUser(upmsUserDTO3);


    }

    @Test
    public void testMapperAddUsers(){

        UpmsUserEntity upmsUserEntity = new UpmsUserEntity();
        upmsUserEntity.setUsername("user5");
        upmsUserEntity.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode("123"));
        upmsUserMapper.insert(upmsUserEntity);
    }

    /**
     *
     * 批量初始化角色权限
     * */
    @Test
    public void batchPermsAdd(){



    }


}
