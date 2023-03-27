package com.minicloud.upms.role.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author alan.wang
 */
@Data
@Builder
@ApiModel(value = "用户角色DTO")
public class UserRoleDTO implements Serializable {


    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
