package com.mayikt.user.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel("用户")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysUserReqBO implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "姓名",required = true)
    @NotBlank(message = "用户姓名不能为空")
//    @LengthValidator(min=2,max=32,message="用户姓名长度在2到32位之间")
    private String userName;

    @ApiModelProperty(value = "账号",required = true)
    @NotBlank(message = "账号不能为空")
//    @LengthValidator(min=4,max=32,message="账号长度在4到32位之间")
    private String account;

    @ApiModelProperty(value = "所属机构ID",required = true)
    private String password;
    @NotNull(message = "所属机构ID不能为空")
    @ApiModelProperty(value = "所属机构ID")
    private Long orgId;


    @ApiModelProperty(value = "性别,1-男,2-女",required = true)
//    @FlagValidator(value = {"1","2"}, message = "性别不正确")
    private Integer sex;

    @ApiModelProperty(value = "手机号码",required = true)
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp="^1[3-9]\\d{9}$",message = "手机号码不正确")
    private String mobile;

    @ApiModelProperty(value = "办公电话")
    @Pattern(regexp="^\\s*|0\\d{2,3}-?\\d{7,8}$",message = "办公电话不正确")
    private String officeTel;

    @ApiModelProperty(value = "微信号")
    @Pattern(regexp="^\\s*|[a-zA-Z][a-zA-Z\\d_-]{5,19}$",message = "微信号不正确")
    private String wechat;

    @ApiModelProperty(value = "邮箱",required = true)
    @Email
    private String email;

    @ApiModelProperty(value = "QQ号码")
    @Pattern(regexp="^\\s*|[1-9][0-9]{4,12}$",message = "QQ号码不正确")
    private String qq;

    @ApiModelProperty(value = "其他联系方式")
    @Pattern(regexp="^\\s*|1[3-9]\\d{9}|0\\d{2,3}-?\\d{7,8}$",message = "其他联系方式不正确")
    private String otherContact;

    @ApiModelProperty(value = "备注")
//    @LengthValidator(max=256,message="备注长度最大256位")
    private String remark;

    @ApiModelProperty(value = "1 正常 2锁定",required = true)
//    @FlagValidator(value = {"1","2"}, message = "状态不正确")
    private Integer status;

}
