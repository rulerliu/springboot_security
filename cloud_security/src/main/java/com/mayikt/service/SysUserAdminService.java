package com.mayikt.service;

import com.mayikt.bo.AdminUserDetails;
import com.mayikt.model.SysMenu;
import com.mayikt.model.SysRole;

import java.util.List;

public interface SysUserAdminService {
    AdminUserDetails loadUserByUsername(String userName, boolean reloadFlag);
    List<SysMenu> listAllMenu();
    public List<SysMenu> getMenus(Long userId, boolean reloadFlag);
    public List<SysRole> getRoles(Long userId, boolean reloadFlag);

}
