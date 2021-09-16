package com.mayikt.security.service;

import com.mayikt.security.bo.AdminUserDetails;
import com.mayikt.user.model.SysMenu;
import com.mayikt.user.model.SysRole;

import java.util.List;

public interface SysUserAdminService {
    AdminUserDetails loadUserByUsername(String userName, boolean reloadFlag);
    List<SysMenu> listAllMenu();
    public List<SysMenu> getMenus(Long userId, boolean reloadFlag);
    public List<SysRole> getRoles(Long userId, boolean reloadFlag);

}