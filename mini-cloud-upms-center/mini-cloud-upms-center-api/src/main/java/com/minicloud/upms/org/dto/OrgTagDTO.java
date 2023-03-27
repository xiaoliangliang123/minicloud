package com.minicloud.upms.org.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "组织机构标签DTO")
public class OrgTagDTO implements Serializable {



    /**
     * 组织标签id
     */
    @ApiModelProperty(value = "组织机构标签id")
    private Integer orgTagId;

    /**
     * 组织标签名称
     */
    @ApiModelProperty(value = "组织机构标签名称")
    private String orgTagName;
}
