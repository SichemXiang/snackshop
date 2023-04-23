package com.snackshop.controller.WxController;


import com.github.pagehelper.Page;
import com.snackshop.entity.SsOrder;
import com.snackshop.entity.SsOrderDetail;
import com.snackshop.properties.WeixinpayProperties;
import com.snackshop.service.SsOrderDetailService;
import com.snackshop.service.SsOrderService;
import com.snackshop.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * @Author: xsz
 * @Description: 订单控制器
 * @DateTime: 2023/4/6 7:21
 **/

@RestController
@Api(tags = "创建订单")
@RequestMapping("/my/order")
@Slf4j
public class WxOrderController {

    private final SsOrderDetailService orderDetailService;

    private final SsOrderService orderService;

    private final TokenUtil tokenUtil;

    private final WeixinpayProperties weixinpayProperties;

    public WxOrderController(SsOrderDetailService orderDetailService, SsOrderService orderService, TokenUtil tokenUtil, WeixinpayProperties weixinpayProperties) {
        this.orderDetailService = orderDetailService;
        this.orderService = orderService;
        this.tokenUtil = tokenUtil;
        this.weixinpayProperties = weixinpayProperties;
    }


    @ApiOperation(value = "创建订单")
    @Transactional
    @PostMapping("/create")
    public Result createOrder(@RequestBody SsOrder order, @RequestHeader (value = "token") String token){
        System.out.println("token===="+token);
        System.out.println("ssorder====="+order);
        System.out.println("openid====="+tokenUtil.getOpenid(token));
        order.setUserId(tokenUtil.getOpenid(token));
        //添加订单到数据库
        order.setOrderNum("snackshop"+ DateUtil.getCurrentDateStr());
        order.setCreateTime(new Date());
        SsOrderDetail[] goods = order.getGoods();
        orderService.insert(order);
        int orderId = orderService.selectOrderIdByOrderNum(order.getOrderNum());
        Arrays.stream(goods).forEach(orderDetail -> {
            orderDetail.setOrderId(orderId);
            orderDetailService.insert(orderDetail);
        });

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("orderNumber",order.getOrderNum());
        return Result.success("订单成功",resultMap);

    }

    @ApiOperation(value = "订单支付")
    @PostMapping("/preparePay")
    public Result preparePay(@RequestBody String orderNum)  {
        System.out.println("ordernum===="+orderNum);
        SsOrder order = orderService.findByOrderNum(orderNum);
        if(order!=null && order.getOrderStatus()==1){
            order.setPayTime(new Date());
            order.setOrderStatus(2); // 设置已经支付状态
            orderService.update(order);
        }else{
            return Result.success("支付失败！");
        }
        return Result.success("支付成功！");
    }


    /**
     * 订单查询 type值 0 全部订单  1 待付款  2 待收货 3 退款/退货
     */

    @ApiOperation(value = "根据type订单查询")
    @GetMapping("/orderList")
    public Result orderList(Integer type,Integer pageNumber,Integer pageSize){
        log.info("type"+type);
        Queryinfo queryinfo = new Queryinfo(pageNumber,pageSize);
        List<SsOrder> orderList ;
        Page<SsOrder> pageOrder;
        Map<String,Object>  resultMap = new HashMap<>();
        if(type == 0){
            //orderService.findOrder();
            pageOrder = orderService.findOrder(queryinfo);
            log.info("总记录数"+pageOrder.getTotal());
            log.info("总页数"+pageOrder.getPages());
            log.info("当前页数据"+pageOrder.getResult());
            orderList = pageOrder.getResult();
            resultMap.put("total",pageOrder.getTotal());
            resultMap.put("totalPage",pageOrder.getPages());
        }else {
            orderList = orderService.findByType(type);
        }
        resultMap.put("orderList",orderList);
        return Result.success("查询订单成功",resultMap);
    }

}
