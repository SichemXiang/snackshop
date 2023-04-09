package com.snackshop.controller;

import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试接口")
public class tsetController {

    @ApiOperation(value = "测试test")
    @PreAuthorize("hasAnyRole('17843815004')")
    @GetMapping("/test")
    public Result func(){
        return Result.success("ok","hello");
    }
}
