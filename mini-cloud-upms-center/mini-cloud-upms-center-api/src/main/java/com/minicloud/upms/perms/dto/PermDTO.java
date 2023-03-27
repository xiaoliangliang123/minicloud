package com.minicloud.upms.perms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author alan.wang
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "权限DTO")
public class PermDTO implements Serializable {

    public PermDTO(){}

    /**
     * 权限id
     */
    @ApiModelProperty(value = "权限id")
    private String permId;

    /**
     * 路由名称
     * */
    private String name;

    /**
     * 权限描述
     */
    private String permDesc;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限url
     */
    private String permUrl;

    /**
     *  前端路由
     * */
    private String path;

    /**
     * 权限方法
     */
    private String permMethod;

    /**
     * 所属服务
     */
    private String permServer;

    /**
     * 所属角色id
     */
    private Integer roleId;

    /**
     * 所属角色code
     */
    private String roleCode;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermDTO that = (PermDTO) o;
        return
                Objects.equals(path, that.path) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash( permUrl);
    }
}
