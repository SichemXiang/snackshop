package com.snackshop.controller;

import com.snackshop.controller.WxController.WxUserController;
import com.snackshop.service.SsWxUserService;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/19 1:38
 **/

@RestController
@RequestMapping("/userManage")
public class UserManageController {

    private final SsWxUserService wxUserService;

    public UserManageController(SsWxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody Queryinfo queryinfo){
        return wxUserService.findPage(queryinfo);
    }

}
