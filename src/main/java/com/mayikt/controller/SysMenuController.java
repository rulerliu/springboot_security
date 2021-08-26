package com.mayikt.controller;

import com.mayikt.mapper.SysMenuMapper;
import com.mayikt.model.SysMenu;
import com.mayikt.utils.RedisService;
import com.mayikt.vo.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 用户控制器
 * @author Louis
 * @date Jun 29, 2019
 */
@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    SysMenuMapper sysMenuMapper;
    @Autowired
    RedisService redisService;

    @GetMapping(value="/list")
    public HttpResult list() {
        List<SysMenu> sysMenus = sysMenuMapper.selectList(null);
        redisService.set("sysMenu", sysMenus);
        redisService.set("sysMenu1", sysMenus.get(0));
        return HttpResult.ok(sysMenus);
    }

}