package com.minicloud.upms.user.service;

import com.minicloud.common.core.page.PageReq;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.user.dto.UserDTO;

import java.util.List;

/**
 * @Author alan.wang
 * @desc: 用户相关操作service
 */
public interface UserService {


    /**
     * @desc :保存upms user
     * @param upmsUserDTO
     * @return 保存后用户的userId
     * */
    Integer saveOrEditUser(UserDTO upmsUserDTO);



    /**
     * @desc :根据userId 查询user
     * @param userId
     * @return  返回userDTO
     * */
    UserDTO findUserById(Integer userId);


    /**
     * @desc :根据username 查询user
     * @param username
     * @return  返回userDTO
     * */
    UserDTO findByUsername(String username);

    /**
     * @desc : 查询所有 user
     * @return  返回List<userDTO>
     * */
    List<UserDTO> queryAllUsers();

    PageReq page(PageReq pageReq, RoleDTO upmsRoleDTO);

    UserDTO findByTenantIdAndUsername(Integer tenantId, String username);

    boolean deleteByUserId(Integer userId);
}
