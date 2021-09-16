package com.mayikt.security.component;

import cn.hutool.json.JSONUtil;
import com.mayikt.security.vo.HttpResult;
import com.mayikt.security.vo.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(HttpResult.error(HttpStatus.SC_UNAUTHORIZED, "暂未登录或token已经过期")));
        response.getWriter().flush();
    }
}
