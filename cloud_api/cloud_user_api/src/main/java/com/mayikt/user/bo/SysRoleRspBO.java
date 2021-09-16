package com.mayikt.user.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @date: 2020/5/18 17:21
 * @description:
 */
@ApiModel("角色")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRoleRspBO implements Serializable {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型，1-一级，2-二级，3-三级,4-四级")
    private Integer roleType;

    @ApiModelProperty(value = "状态，0-有效，1-无效")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    private List<SysUserRoleRspBO> sysUserRoleRspBOList;

    private List<SysRoleMenuRspBO> sysRoleMenuRspBOList;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SysRoleRspBO)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        SysRoleRspBO role = (SysRoleRspBO) obj;
        return role.getRoleId().equals(this.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }

}
