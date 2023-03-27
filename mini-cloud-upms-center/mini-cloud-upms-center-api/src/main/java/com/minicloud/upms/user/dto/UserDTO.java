package com.minicloud.upms.user.dto;


import com.minicloud.upms.perms.dto.PermDTO;
import com.minicloud.upms.role.dto.RoleDTO;
import com.minicloud.upms.role.dto.UserRoleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 * @desc
 */

@Data
@AllArgsConstructor
@ApiModel(value = "用户DTO")
public class UserDTO implements Serializable {


    public UserDTO(){};

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 可用 0:可用，1：禁用
     */
    @ApiModelProperty(value = "是否禁用",allowableValues = "0:可用，1：禁用")
    private Boolean active;

    /**
     * 已删除 0：未删除 ，1：已删除
     */
    @ApiModelProperty(value = "是否删除",allowableValues = "0:未删除，1：已删除")
    private Boolean deleted;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效时间")
    private Date expireTime;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像",notes = "需要开启mini-cloud-files结合minio使用")
    private String headimg;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",notes = "密文")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",notes = "不可变")
    private String username;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer deptId;


    /**
     * 用户角色集
     */
    @ApiModelProperty(value = "用户角色集合")
    private List<RoleDTO> userRoles;

    @Deprecated
    @ApiModelProperty(value = "权限集合",notes = "已废弃")
    private List<PermDTO> permDTOS;

    public List<UserRoleDTO> userRoleDTOS() {
        return Objects.isNull(userRoles)? Collections.emptyList():userRoles.stream().parallel().map(roleDTO -> UserRoleDTO.builder().roleId(roleDTO.getRoleId()).userId(userId).build()).collect(Collectors.toList());
    }

    public List<PermDTO> getPermDTOS() {
        return permDTOS;
    }

    public List<RoleDTO> getRoleDTOS() {
        return userRoles;
    }


}
