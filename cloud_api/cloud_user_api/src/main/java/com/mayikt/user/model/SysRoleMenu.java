package com.mayikt.user.model;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "角色菜单")
public class SysRoleMenu {
    @TableId("role_menu_id")
    @ApiModelProperty(value = "序号")
    private Long roleMenuId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "功能ID")
    private Long menuId;

    private static final long serialVersionUID = 1L;

}