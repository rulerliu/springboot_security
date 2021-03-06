package com.mayikt.bo;

import com.mayikt.model.SysMenu;
import com.mayikt.model.SysRole;
import com.mayikt.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserDetails implements UserDetails {
    private SysUser sysUser;
    private List<SysMenu> menus;
    private List<SysRole> roles;

    public AdminUserDetails() {}

    public AdminUserDetails(SysUser sysUser, List<SysMenu> menus, List<SysRole> roles) {
        this.sysUser = sysUser;
        this.menus = menus;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回当前用户的角色
        return menus.stream()
                .filter(menu -> StringUtils.isNotBlank(menu.getAuthTag()))
                .map(menu ->new SimpleGrantedAuthority(menu.getAuthTag()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getStatus().equals(1);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

}
