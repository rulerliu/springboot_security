package com.mayikt.user.controller;

import com.mayikt.user.api.SysUserService;
import com.mayikt.user.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/selectList")
    public List<SysUser> selectList(@RequestParam("userName") String userName) {
        System.out.println("=================");
        return sysUserService.selectList(userName);
    }

}
