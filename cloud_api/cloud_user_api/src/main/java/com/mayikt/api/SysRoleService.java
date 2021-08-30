package com.mayikt.api;

import com.mayikt.bo.SysRoleReqBO;
import com.mayikt.bo.SysRoleRspBO;
import com.mayikt.model.SysRole;
import com.mayikt.vo.CommonPage;
import com.mayikt.vo.CommonResult;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SysRoleService {
    CommonResult addRole(SysRoleReqBO sysRoleReqBO);

    CommonResult updateRole(SysRoleReqBO sysRoleReqBO);

    CommonResult deleteRole(Long roleId);

    public CommonPage list(Integer pageNum, Integer pageSize, SysRoleReqBO sysRoleReqBO, Authentication authentication);

    SysRoleRspBO getByRoleId(Long roleId);

    List<SysRole> getRoles(Long userId);

    void checkRoleDataPermission(Authentication authentication, SysRoleReqBO sysRoleReqBO);
}
