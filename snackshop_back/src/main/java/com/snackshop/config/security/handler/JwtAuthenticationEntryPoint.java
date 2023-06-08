package com.snackshop.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snackshop.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: xsz
 * @Description: 用户未登录的情况下访问资源
 * @DateTime: 2023/3/5 2:49
 **/

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(Result.fail("JwtAuthenticationEntryPoint:您尚未登陆，请登录后操作!")));
        writer.flush();
        writer.close();
    }
}
