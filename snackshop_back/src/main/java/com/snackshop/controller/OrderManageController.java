package com.snackshop.controller;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsOrder;
import com.snackshop.service.SsOrderDetailService;
import com.snackshop.service.SsOrderService;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/17 0:29
 **/

@RestController
@RequestMapping("/orderManage")
public class OrderManageController {

    private final SsOrderService orderService;
    private final SsOrderDetailService orderDetailService;

    public OrderManageController(SsOrderService orderService, SsOrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }




    @PostMapping("/findOrderList")
    public Result findOrderList(@RequestBody Queryinfo queryinfo){
        Page<SsOrder> page= orderService.findOrder(queryinfo);
        long total = page.getTotal();
        List<SsOrder> orderList= page.getResult();
        Map<String,Object> map=new HashMap<>();
        map.put("total",total);
        map.put("orderList",orderList);

        return Result.success("分页查询订单成功！",map);
    }


    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody SsOrder order){
        return orderService.updateStatus(order);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public Result delete(@PathVariable(value = "orderId") int orderId){
        orderService.delete(orderId);
        orderDetailService.deleteByOrderId(orderId);
        return Result.success("删除成功！");
    }
}
