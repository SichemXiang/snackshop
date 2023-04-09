package com.snackshop.controller.WxController;


import com.snackshop.entity.SsWxUser;
import com.snackshop.service.SsWxUserService;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/22 3:19
 **/
@RestController
@Api(tags = "微信用户管理")
@RequestMapping("/wxUser")
public class WxUserController {

    @Autowired
    private SsWxUserService wxUserService;


    @ApiOperation(value = "添加用户")
    @PostMapping("/insert")
    public Result insert(@RequestBody SsWxUser wxUser){
        return wxUserService.insert(wxUser);
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody Queryinfo queryinfo){
        return wxUserService.findPage(queryinfo);
    }


}
