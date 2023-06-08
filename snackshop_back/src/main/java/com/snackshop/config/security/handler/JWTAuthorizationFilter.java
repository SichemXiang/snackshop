package com.snackshop.config.security.handler;

import com.snackshop.config.security.MyUserDetailServiceImpl;
import com.snackshop.entity.SsUser;
import com.snackshop.util.RedisUtil;
import com.snackshop.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: xsz
 * @Description: token认证 在接口访问前进行过滤
 * @DateTime: 2023/3/4 23:56
 **/
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private  MyUserDetailServiceImpl myUserDetailService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取token
        String header = request.getHeader(tokenHeader);
        System.out.println("header=========="+header);
        //判断token是否存在
        if (null != header && header.startsWith(tokenHead)) {
            //拿到token主体
            String token = header.substring(tokenHead.length());
            //根据token获取用户名
            String username = tokenUtil.getUsernameByToken(token);

            //token存在 但是没有用户信息
            if(!redisUtil.haskey("userInfo_"+username)){
                throw new UsernameNotFoundException("您尚未登录");
            }
            UserDetails userDetails =  (UserDetails)redisUtil.getValue("userInfo_"+username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        //过滤器放行
        chain.doFilter(request,response);
    }
}
