package com.minicloud.upms;

import com.minicloud.upms.perms.dto.PermDTO;
import com.minicloud.upms.perms.service.PermService;
import com.minicloud.upms.role.service.RoleService;
import com.minicloud.upms.user.dto.UserDTO;
import com.minicloud.upms.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @Author alan.wang
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniCloudUpmsApplication.class)

public class UpmsUserTest {

    @Autowired
    private UserService upmsUserService;

    @Autowired
    private RoleService upmsRoleService;

    @Autowired
    private PermService upmsPermService;

    @Test
    public void testQueryUserByUserId() {

        Integer userId = 24;
        UserDTO upmsUserDTO = upmsUserService.findUserById(userId);
        log.info(upmsUserDTO.toString());
    }

    @Test
    public void testQueryUserByUsername() {

        String username = "user4";
        UserDTO upmsUserDTO = upmsUserService.findByUsername(username);
        log.info(upmsUserDTO.toString());
    }

    @Test
    public void testQueryRolePermsByRoleIds() {

        Integer[] roleIds = {34};
        List<PermDTO> upmsPermDTOS = upmsPermService.queryRolesPermsByRoleIds(roleIds);
        upmsPermDTOS.parallelStream().forEach(upmsPermDTO -> {
            System.out.println(upmsPermDTO.getRoleId()+":"+upmsPermDTO.getPermUrl());
        });
    }
}
