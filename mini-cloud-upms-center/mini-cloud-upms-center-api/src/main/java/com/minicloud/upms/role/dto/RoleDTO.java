package com.minicloud.upms.role.dto;



import com.minicloud.upms.perms.dto.PermDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author alan.wang
 */
@Data
@AllArgsConstructor
@Builder
@ApiModel(value = "角色DTO")
public class RoleDTO implements Serializable {


    public RoleDTO(){}
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;
    /**
     * 角色权限集
     */
    @Deprecated
    @ApiModelProperty(value = "权限集合",notes = "已废弃")
    private List<PermDTO> permDTOS;


    public List<PermDTO> getPermDTOS() {

        if (permDTOS == null) {
            return Collections.EMPTY_LIST;
        }
        return permDTOS.stream().map(permDTO -> {
            permDTO.setRoleCode(roleCode);
            return permDTO;
        }).collect(Collectors.toList());
    }


}
