package com.mayikt.security.service;


import com.mayikt.user.model.SysMenu;
import com.mayikt.user.model.SysOrg;
import com.mayikt.user.model.SysRole;
import com.mayikt.user.model.SysUser;

import java.util.List;

public interface UmsAdminCacheService {
    /**
     * 获取缓存后台用户信息
     * @param account
     * @return
     */
    SysUser getSysUser(String account);

    /**
     * 设置缓存后台用户信息
     * @param admin
     */
    void setSysUser(SysUser admin);

    /**
     * 删除缓存用户信息
     * @param account
     */
    void deleteSysUser(String account);

    /**
     * 获取缓存后台用户菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> getMenus(Long userId);

    /**
     * 设置后台后台用户菜单列表
     * @param userId
     * @param menus
     */
    void setMenus(Long userId, List<SysMenu> menus);

    /**
     * 删除缓存用户菜单
     * @param userId
     */
    void deleteMenus(Long userId);

    /**
     * 获取缓存后台用户角色列表
     * @param userId
     * @return
     */
    List<SysRole> getRoles(Long userId);

    /**
     * 设置后台后台用户角色列表
     * @param userId
     * @param Roles
     */
    void setRoles(Long userId, List<SysRole> Roles);


    /**
     * 删除缓存用户角色
     * @param userId
     */
    void deleteRoles(Long userId);

    /**
     * 获取缓存机构信息
     * @param orgId
     * @return
     */
    SysOrg getSysOrg(Long orgId);

    /**
     * 设置缓存机构信息
     */
    void setSysOrg(SysOrg sysOrg);

    /**
     * 删除缓存机构信息
     * @param orgId
     */
    void deleteSysOrg(Long orgId);

}
