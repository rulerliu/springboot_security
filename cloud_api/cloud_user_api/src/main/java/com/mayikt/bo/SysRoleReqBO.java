package com.mayikt.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @date: 2020/5/18 15:17
 * @description:
 */
@ApiModel("角色")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRoleReqBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "角色名称",required = true)
    @NotBlank(message = "角色名称不能为空")
//    @LengthValidator(max=64,message="角色名称长度最大64位")
    private String roleName;

    @ApiModelProperty(value = "角色类型，1-一级，2-二级，3-三级,4-四级",required = true)
    @NotNull(message = "角色类型不能为空")
    private Integer roleType;

    @ApiModelProperty(value = "状态，0-有效，1-无效")
    private String status;

    @ApiModelProperty(value = "备注")
//    @LengthValidator(max=256,message="备注长度最大256位")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<SysUserRoleReqBO> userRoleReqBOS;

    private List<SysRoleMenuReqBO> roleMenuReqBOS;


}
