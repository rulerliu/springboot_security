package com.mayikt.user.controller;

import com.mayikt.user.api.SysOrgService;
import com.mayikt.user.model.SysOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org")
public class SysOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    @RequestMapping("/selectById")
    public SysOrg selectById(@RequestParam("orgId") Long orgId) {
        System.out.println("=================");
        return sysOrgService.getById(orgId);
    }

}
