package com.mayikt.user.controller;

import com.mayikt.user.api.SysRoleService;
import com.mayikt.user.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/selectRolesByUserId")
    public List<SysRole> selectRolesByUserId(@RequestParam("userId") Long userId) {
        System.out.println("=================");
        return sysRoleService.getRoles(userId);
    }

}
