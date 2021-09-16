package com.mayikt.security.controller;

import com.mayikt.security.vo.HttpResult;
import com.mayikt.security.vo.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 登录控制器
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

//    @RequestMapping("/test")
//    public List<SysUser> selectList(@RequestParam("userName") String userName) {
//        return cloudUserFeign.selectSysUserList(userName);
//    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        
        // 系统登录认证
//        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);

        return HttpResult.ok("token");
    }

}