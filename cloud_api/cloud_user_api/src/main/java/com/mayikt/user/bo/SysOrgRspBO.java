package com.mayikt.user.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "机构")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SysOrgRspBO implements Serializable {
    @ApiModelProperty(value = "机构ID")
    private Long orgId;

    @ApiModelProperty(value = "父机构ID")
    private Long parentId;

    @ApiModelProperty(value = "父机构名称")
    private String parentName;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "机构类型,1-单位,2-部门,3-岗位")
    private Integer orgType;

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

    @ApiModelProperty(value = "状态,0-有效,1-无效")
    private Integer status;

    @ApiModelProperty(value = "排序，数字越小排最前")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}