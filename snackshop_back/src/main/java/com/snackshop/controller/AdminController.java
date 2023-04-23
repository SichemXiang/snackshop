package com.snackshop.controller;

import com.snackshop.entity.SsModifyPassword;
import com.snackshop.service.SsUserService;
import com.snackshop.util.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/18 21:12
 **/

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final SsUserService userService;

    private final PasswordEncoder passwordEncoder;

    public AdminController(SsUserService userService,PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/modifyPassword")
    public Result modifyPassword(@RequestBody SsModifyPassword modifyPassword){
        String oldPassword = userService.loadUserByUsername(modifyPassword.getPhoneNumber()).getPassword();
        System.out.println(oldPassword);
        if(!passwordEncoder.matches(modifyPassword.getOldPassword(),oldPassword )) {
            return Result.fail("原密码错误");
        }
        String password = passwordEncoder.encode(modifyPassword.getNewPassword());
        System.out.println(password);
         return userService.updatePassword(modifyPassword.getPhoneNumber(), password);
    }
}
