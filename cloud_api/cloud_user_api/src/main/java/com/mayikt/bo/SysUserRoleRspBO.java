package com.mayikt.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "用户角色")
@Data
public class SysUserRoleRspBO implements Serializable {
    @ApiModelProperty(value = "序号")
    private Long userRoleId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
}
