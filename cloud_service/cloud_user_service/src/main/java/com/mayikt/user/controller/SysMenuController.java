package com.mayikt.user.controller;

import com.mayikt.user.api.SysMenuService;
import com.mayikt.user.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/selectListByStatus")
    public List<SysMenu> selectListByStatus(@RequestParam("status") Integer status) {
        System.out.println("=================");
        return sysMenuService.selectList(status);
    }

    @RequestMapping("/selectListByUserId")
    public List<SysMenu> selectListByUserId(@RequestParam("userId") Long userId) {
        System.out.println("=================");
        return sysMenuService.getMenus(userId);
    }

}
