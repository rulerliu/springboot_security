package com.mayikt.user.controller;

import com.mayikt.user.api.SysMenuService;
import com.mayikt.user.model.SysMenu;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Api(tags = "SysMenuController", description = "菜单管理")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('system:menu:list')")
    @RequestMapping("/selectListByStatus")
    public List<SysMenu> selectListByStatus(@RequestParam("status") Integer status) {
        System.out.println("=================");
        return sysMenuService.selectList(status);
    }

    @PreAuthorize("hasAuthority('system:menu:list')")
    @RequestMapping("/selectListByUserId")
    public List<SysMenu> selectListByUserId(@RequestParam("userId") Long userId) {
        System.out.println("=================");
        return sysMenuService.getMenus(userId);
    }

}
