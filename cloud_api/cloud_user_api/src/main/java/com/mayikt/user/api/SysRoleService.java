package com.mayikt.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mayikt.security.vo.CommonResult;
import com.mayikt.user.bo.SysRoleReqBO;
import com.mayikt.user.bo.SysRoleRspBO;
import com.mayikt.user.model.SysRole;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    CommonResult addRole(SysRoleReqBO sysRoleReqBO);

    CommonResult updateRole(SysRoleReqBO sysRoleReqBO);

    CommonResult deleteRole(Long roleId);

//  CommonPage list(Integer pageNum, Integer pageSize, SysRoleReqBO sysRoleReqBO, Authentication authentication);

    SysRoleRspBO getByRoleId(Long roleId);

    List<SysRole> getRoles(Long userId);

    void checkRoleDataPermission(Authentication authentication, SysRoleReqBO sysRoleReqBO);
}
