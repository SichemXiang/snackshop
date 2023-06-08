package com.snackshop.controller.WxController;


import com.snackshop.entity.SsWxUser;
import com.snackshop.service.SsWxUserService;
import com.snackshop.util.DateUtil;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import com.snackshop.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private TokenUtil tokenUtil;


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

    @ApiOperation(value = "上传头像")
    @PostMapping("/uploadAvatar")
    public String updateUserInfo(@RequestParam("avatar") MultipartFile file) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        if(!file.isEmpty()){
            String originalFilename = file.getOriginalFilename();
            System.out.println(originalFilename);
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),
                    new File("C://Users/1972454587/Desktop/finally/code/images/avatar/"+newFileName));
            return newFileName;

        }
        return "失败";
    }

    @PostMapping("/my/updateWxUserInfo")
    public Result updateWxUserInfo(@RequestBody SsWxUser wxUser,@RequestHeader (value = "token") String token){
        wxUser.setOpenId(tokenUtil.getOpenid(token));
        wxUserService.updateWxUserInfo(wxUser);
        return Result.success("修改成功！");
    }



}
