package com.snackshop.util;

import com.snackshop.entity.SsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @Author: xsz
 * @Description: 用于获取当前用户的基本信息
 * @DateTime: 2023/3/10 5:16
 **/
@Slf4j
public class SecurityUtil {

    //从security中获取用户信息
    public static SsUser getUser(){
        SsUser ssRole = (SsUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ssRole.setPassword(null);
        ssRole.setFrontAvatar(ssRole.getAvatar());
        return ssRole;
    }

    //security中获取用户名
    public static String getUsername(){
        return getUser().getUsername();
    }


    public static int getUserId(){
        return getUser().getUserId();
    }
}
