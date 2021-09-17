package com.mayikt.user.model;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "菜单")
public class SysMenu implements Serializable {
    @TableId("menu_id")
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单URL")
    private String menuUrl;

    @ApiModelProperty(value = "授权标识，(多个用逗号分隔，如：user:list,user:create)")
    private String authTag;

    @ApiModelProperty(value = "菜单类型,1-目录,2-菜单,3-按钮")
    private Integer menuType;

    @ApiModelProperty(value = "排序，数字越小越排在前")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态 0有效 1无效")
    private Integer status;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否显示 1显示 0不显示")
    private Integer showFlag;

}