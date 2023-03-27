package com.minicloud.upms.perms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author alan.wang
 */
@Deprecated
@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "角色权限DTO")
public class RolePermDTO implements Serializable {

    public RolePermDTO(){}

    /**
     * 权限url
     */
    @ApiModelProperty(value = "权限id")
    private Integer permId;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
