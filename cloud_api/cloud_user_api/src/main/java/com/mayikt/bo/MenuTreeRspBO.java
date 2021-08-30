package com.mayikt.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("菜单树")
public class MenuTreeRspBO implements Serializable {
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message="参数名称不能为空")
    private String menuName;

    @ApiModelProperty(value = "菜单URL")
    @NotBlank(message="参数类型不能为空")
    private String menuUrl;

    @ApiModelProperty(value = "授权标识，(多个用逗号分隔，如：user:list,user:create)")
    private String authTag;

    @ApiModelProperty(value = "菜单类型,1-目录,2-菜单,3-按钮")
    @NotBlank(message="参数值不能为空")
    private Integer menuType;

    @ApiModelProperty(value = "排序，数字越小越排在前")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态 0有效 1无效")
    private Integer status;

    @ApiModelProperty(value = "图标")
    private String icon;
    private List<MenuTreeRspBO> children;
}
