package com.mayikt.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户")
@TableName("sys_user")
public class SysUser implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码密文")
    private String password;

    @ApiModelProperty(value = "所属机构ID")
    private Long orgId;

    @ApiModelProperty(value = "性别,1-男,2-女")
    private Integer sex;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "办公电话")
    private String officeTel;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "QQ号码")
    private String qq;

    @ApiModelProperty(value = "其他联系方式")
    private String otherContact;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "1 正常 2锁定")
    private Integer status;

}