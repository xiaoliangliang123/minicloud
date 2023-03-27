package com.minicloud.upms.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author alan.wang
 */
@Data
@ApiModel(value = "组织机构DTO")
public class OrgDTO implements Serializable {


    @ApiModelProperty(value = "组织机构id")
    private Integer orgId;

    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称")
    private String orgName;

    /**
     * 父级组织id
     */
    @ApiModelProperty(value = "组织机构父id")
    private Integer orgParentId;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "组织机构标签id")
    private Integer orgTagId;


    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id")
    private Integer tenantId;

}
