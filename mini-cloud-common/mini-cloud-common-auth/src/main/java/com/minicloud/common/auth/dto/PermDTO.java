package com.minicloud.common.auth.dto;

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
public class PermDTO implements Serializable {

    public PermDTO(){}

    /**
     * 权限id
     */
    private String permId;

    /**
     * 权限描述
     */
    private String permDesc;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限类型
     */
    private String permType ;

    /**
     * 权限url
     */
    private String permUrl;

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
                Objects.equals(permUrl, that.permUrl) &&
                Objects.equals(permMethod, that.permMethod) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash( permUrl, permMethod);
    }
}
