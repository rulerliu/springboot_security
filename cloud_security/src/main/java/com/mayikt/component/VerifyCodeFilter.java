package com.mayikt.component;

import cn.hutool.json.JSONUtil;
import com.mayikt.exception.VerificationCodeException;
import com.mayikt.vo.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler=new AuthenticationFailureHandler(){
        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control","no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JSONUtil.parse(HttpResult.error("验证码错误")));
            response.getWriter().flush();
        }
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(!"/system/user/login".equals(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        boolean flag=verifyCode(request);
        if(!flag){
            authenticationFailureHandler.onAuthenticationFailure(request,response,new VerificationCodeException());
        }else{
            filterChain.doFilter(request,response);
        }
    }

    private boolean verifyCode(HttpServletRequest request){
        String captcha = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String saveCode = (String)session.getAttribute("captcha");
        if(!StringUtils.isEmpty(saveCode)){
            session.removeAttribute("captcha");
        }
        if(StringUtils.isBlank(captcha) || StringUtils.isBlank(saveCode) || !captcha.equals(saveCode)){
            return false;
        }
        return true;
    }
}
