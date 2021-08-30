package com.mayikt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.mayikt.bo.AdminUserDetails;
import com.mayikt.mapper.SysMenuMapper;
import com.mayikt.mapper.SysOrgMapper;
import com.mayikt.mapper.SysRoleMapper;
import com.mayikt.mapper.SysUserMapper;
import com.mayikt.model.SysMenu;
import com.mayikt.model.SysOrg;
import com.mayikt.model.SysRole;
import com.mayikt.model.SysUser;
import com.mayikt.service.SysUserAdminService;
import com.mayikt.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysUserAdminServiceImpl implements SysUserAdminService {
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Override
    public AdminUserDetails loadUserByUsername(String userName, boolean reloadFlag) {
        SysUser sysUser = getSysUserByAccount(userName);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        if (!sysUser.getStatus().equals(1)) {
            throw new ApiException("账号被锁定");
        }
        AdminUserDetails userDetails = new AdminUserDetails(sysUser, getOrgById(sysUser.getOrgId(), reloadFlag),
                getMenus(sysUser.getUserId(), reloadFlag), getRoles(sysUser.getUserId(), reloadFlag));
        return userDetails;
    }

    @Override
    public List<SysMenu> listAllMenu() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysMenu::getStatus, 0);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
        return sysMenus;
    }

    @Override
    public List<SysMenu> getMenus(Long userId, boolean reloadFlag) {
        List<SysMenu> menus = null;
        if (!reloadFlag) {
            menus = adminCacheService.getMenus(userId);
            if (!CollectionUtils.isEmpty(menus)) {
                return menus;
            }
        }

        menus = sysMenuMapper.getMenus(userId);
        if (!CollectionUtils.isEmpty(menus)) {
            adminCacheService.setMenus(userId, menus);
        }
        return menus;
    }

    @Override
    public List<SysRole> getRoles(Long userId, boolean reloadFlag) {
        List<SysRole> sysRoleList = null;
        if (!reloadFlag) {
            sysRoleList = adminCacheService.getRoles(userId);
            if (!CollectionUtils.isEmpty(sysRoleList)) {
                return sysRoleList;
            }
        }

        sysRoleList = sysRoleMapper.getRoles(userId);
        if (!CollectionUtils.isEmpty(sysRoleList)) {
            adminCacheService.setRoles(userId, sysRoleList);
        }

        return sysRoleList;
    }

    public SysUser getSysUserByAccount(String userName) {
        SysUser sysUser = adminCacheService.getSysUser(userName);
        if (sysUser != null) {
            return sysUser;
        }
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUserName, userName);
        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(sysUsers)) {
            adminCacheService.setSysUser(sysUsers.get(0));
            return sysUsers.get(0);
        }
        return null;
    }

    public SysOrg getOrgById(Long orgId, boolean reloadFlag) {
        SysOrg sysOrg = null;
        if (!reloadFlag) {
            sysOrg = adminCacheService.getSysOrg(orgId);
            if (sysOrg != null) {
                return sysOrg;
            }
        }

        sysOrg = sysOrgMapper.selectById(orgId);
        if (sysOrg != null && sysOrg.getStatus().equals(1)) {
            sysOrg = null;
        }
        adminCacheService.setSysOrg(sysOrg);
        return sysOrg;
    }

}
