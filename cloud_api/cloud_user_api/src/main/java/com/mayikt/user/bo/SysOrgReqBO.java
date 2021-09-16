package com.mayikt.user.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "机构")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SysOrgReqBO implements Serializable {
    @ApiModelProperty(value = "机构ID")
    private Long orgId;

    @ApiModelProperty(value = "父机构ID",required = true)
    @NotNull(message = "父机构ID不能为空")
    private Long parentId;

    @ApiModelProperty(value = "机构名称",required = true)
    @NotBlank(message = "机构名称不能为空")
//    @LengthValidator(min=2,max=64,message="账号长度在2到64位之间")
    private String orgName;

    @ApiModelProperty(value = "机构类型,1-单位,2-部门,3-岗位",required = true)
//    @FlagValidator(value = {"1","2","3"}, message = "机构类型不正确")
    private Integer orgType;

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

    @ApiModelProperty(value = "状态,0-有效,1-无效",required = true)
//    @FlagValidator(value = {"0","1"}, message = "状态不正确")
    private Integer status;

    @ApiModelProperty(value = "排序，数字越小排最前",required = true)
    @NotNull(message = "排序不能为空")
    @Min(value=0)
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Integer type;

}