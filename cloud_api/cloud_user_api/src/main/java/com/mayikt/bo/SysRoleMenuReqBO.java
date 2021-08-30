package com.mayikt.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @date: 2020/5/18 15:27
 * @description:
 */
@ApiModel("角色功能菜单")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRoleMenuReqBO implements Serializable {
    @ApiModelProperty(value = "序号")
    private Long roleMenuId;

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @ApiModelProperty(value = "功能ID")
    @NotNull(message = "功能ID不能为空")
    private Long menuId;
}
