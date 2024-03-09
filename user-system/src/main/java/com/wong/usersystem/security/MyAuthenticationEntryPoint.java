package com.wong.usersystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wong.common.utils.CommonResponse;
import com.wong.common.utils.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Wongbuer
 * @Description 自定义登录失败返回
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        CommonResponse<Object> errorResponse = ResultUtil.error(HttpStatus.FORBIDDEN, "禁止访问(无权限)");
        String result = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(result);
    }
}
