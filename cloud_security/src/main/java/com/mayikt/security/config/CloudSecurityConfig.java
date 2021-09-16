package com.mayikt.security.config;

import com.mayikt.security.component.DynamicSecurityService;
import com.mayikt.security.service.SysUserAdminService;
import com.mayikt.user.model.SysMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CloudSecurityConfig extends SecurityConfig {

    @Autowired
    private SysUserAdminService sysUserAdminService;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> sysUserAdminService.loadUserByUsername(username,false);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<SysMenu> resourceList = sysUserAdminService.listAllMenu();
                for (SysMenu resource : resourceList) {
                    if(StringUtils.isEmpty(resource.getMenuUrl()) || StringUtils.isEmpty(resource.getAuthTag())){
                        continue;
                    }
                    map.put(resource.getMenuUrl(), new org.springframework.security.access.SecurityConfig(resource.getAuthTag()));
                }
                return map;
            }
        };
    }
}
