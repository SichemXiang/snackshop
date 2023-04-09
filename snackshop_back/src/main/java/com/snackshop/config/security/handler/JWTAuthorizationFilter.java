package com.snackshop.config.security.handler;

import com.snackshop.config.security.MyUserDetailServiceImpl;
import com.snackshop.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private TokenUtil tokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    private final MyUserDetailServiceImpl myUserDetailService;

    public JWTAuthorizationFilter(TokenUtil tokenUtil, MyUserDetailServiceImpl myUserDetailService) {
        this.tokenUtil = tokenUtil;
        this.myUserDetailService = myUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //从请求头中获取token
        String header = request.getHeader(tokenHeader);
        //判断token是否存在
        if (null != header && header.startsWith(tokenHead)) {
            //拿到token主体
            String token = header.substring(tokenHead.length());
            //根据token后去用户名
            String username = tokenUtil.getUsernameByToken(token);
            //token存在 但是没有用户信息
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                //没有登录信息
                UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
                //判断token是否有效
                if(!tokenUtil.isExpiration(token) && username.equals(userDetails.getUsername())){
                    //刷新security中的用户信息
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        //过滤器放行
        chain.doFilter(request,response);
    }
}
