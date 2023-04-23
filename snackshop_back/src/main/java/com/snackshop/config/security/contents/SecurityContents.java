package com.snackshop.config.security.contents;

/**
 * @Author: xsz
 * @Description: 白名单
 * @DateTime: 2023/3/5 18:23
 **/
public class SecurityContents {
    public static final String[] WHITE_LIST = {
            //后端登陆接口
            "/user/login",

            //swagger相关
            "/swagger-ui.html",
            "/doc.html",
            "/favicon.ico",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**",
            "/configuration/ui",
            "/configuration/security",

            //图片
            "/image/***",
            "/goods/**",

            //小程序接口
            "/wxGoods/**",
            "/wx/**",
            "/wxCategory/**",
            "/wxUser/**",
            "/my/order/**"
    };
}
