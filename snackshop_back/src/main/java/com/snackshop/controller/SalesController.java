package com.snackshop.controller;


import com.snackshop.service.SsOrderService;
import com.snackshop.service.SsSalesService;
import com.snackshop.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/5/29 2:51
 **/
@RestController
@RequestMapping("/goodsSales")
public class SalesController {

    @Autowired
    private SsSalesService ssSalesService;

    @Autowired
    private SsOrderService orderService;

    @GetMapping("/findSalesByTimeRange/{days}")
    public Result findSalesByTimeRange(@PathVariable(value = "days") int days){
        return Result.success("查询成功！",ssSalesService.findSalesByTimeRange(days));
    }

    @GetMapping("/getDailyTransactionStats/{days}")
    public Result getDailyTransactionStats(@PathVariable(value = "days") int days){
        return Result.success("查询成功！",orderService.getDailyTransactionStats(days));
    }




}
