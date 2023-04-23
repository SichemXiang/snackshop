package com.snackshop.controller;

import com.snackshop.service.SsOrderDetailService;
import com.snackshop.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/18 5:18
 **/
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private SsOrderDetailService orderDetailService;

    @GetMapping("/list/{id}")
    public Result orderDetailList(@PathVariable(value = "id")int id){
        return orderDetailService.findByOrderId(id);
    }

}
