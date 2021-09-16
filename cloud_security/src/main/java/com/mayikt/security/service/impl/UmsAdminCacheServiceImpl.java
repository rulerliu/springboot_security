package com.mayikt.security.service.impl;

import com.mayikt.security.service.UmsAdminCacheService;
import com.mayikt.security.utils.RedisService;
import com.mayikt.user.model.SysMenu;
import com.mayikt.user.model.SysOrg;
import com.mayikt.user.model.SysRole;
import com.mayikt.user.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("umsAdminCacheService")
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;
    @Value("${redis.key.roleList}")
    private String REDIS_KEY_ROLE_LIST;
    @Value("${redis.key.standerList}")
    private String REDIS_KEY_STANDER_LIST;
    @Value("${redis.key.sysOrg}")
    private String REDIS_KEY_SYSORG_LIST;

    @Override
    public SysUser getSysUser(String account) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + account;
        return (SysUser) redisService.get(key);
    }

    @Override
    public void setSysUser(SysUser sysUser) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + sysUser.getAccount();
        redisService.set(key, sysUser, REDIS_EXPIRE);
    }

    @Override
    public void deleteSysUser(String account) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + account;
        redisService.del(key);
    }


    @Override
    public List<SysMenu> getMenus(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
        return (List<SysMenu>) redisService.get(key);
    }

    @Override
    public void setMenus(Long userId, List<SysMenu> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }

    @Override
    public void deleteMenus(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
        redisService.del(key);
    }

    @Override
    public List<SysRole> getRoles(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ROLE_LIST + ":" + userId;
        return (List<SysRole>) redisService.get(key);
    }


    @Override
    public void setRoles(Long userId, List<SysRole> sysRoleList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ROLE_LIST + ":" + userId;
        redisService.set(key, sysRoleList, REDIS_EXPIRE);
    }

    @Override
    public void deleteRoles(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ROLE_LIST + ":" + userId;
        redisService.del(key);
    }

    @Override
    public SysOrg getSysOrg(Long orgId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_SYSORG_LIST + ":" + orgId;
        return (SysOrg) redisService.get(key);
    }

    @Override
    public void setSysOrg(SysOrg sysOrg) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_SYSORG_LIST + ":" + sysOrg.getOrgId();
        redisService.set(key, sysOrg, REDIS_EXPIRE);
    }

    @Override
    public void deleteSysOrg(Long orgId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_SYSORG_LIST + ":" + orgId;
        redisService.del(key);
    }

}
