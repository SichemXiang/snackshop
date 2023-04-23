package com.snackshop.controller;

import com.snackshop.service.SsUserService;
import com.snackshop.util.Result;
import com.snackshop.util.SecurityUtil;
import com.snackshop.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
* @Author: xsz
* @Description:
 * 登录
 * 退出
 * 获取当前用户的基本信息
* @DateTime: 15:49 2023/2/24
*/

@RestController
@RequestMapping("/user")
@Api(value = "用户使用接口")
public class LoginController {

    @Autowired
    private SsUserService userService;


    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        return userService.login(loginVo);
    }

    @ApiOperation(value = "获取用户基本信息")
    @GetMapping("/getInfo")
    public Result getUserInfo(Principal principal){
        if(principal == null){
            return Result.fail("请登录！");
        }
        return Result.success("获取用户信息成功", SecurityUtil.getUser());
    }

    //用户退出登录
    @ApiOperation(value = "用户退出登录" )
    @GetMapping("/logout")
    public Result logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Result.success("退出成功！");
    }
}
