package com.snackshop.controller.WxController;

import com.snackshop.service.SsGoodsService;
import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author: xsz
 * @Description: 小程序端商品
 * @DateTime: 2023/3/25 3:14
 **/

@RestController
@Api(tags = "小程序商品查询")
@RequestMapping("/wxGoods")
public class WxGoodsController {

    @Autowired
    private SsGoodsService goodsService;

    @ApiOperation(value = "轮播图查询")
    @GetMapping("/findSwiper")
    public Result findSwiper(){
        return goodsService.findSwiper();
    }

    @ApiOperation(value = "热卖商品查询")
    @GetMapping("/findHot")
    public Result findHot(){
        return goodsService.findHot();
    }

    @ApiOperation(value = "根据id查询商品")
    @GetMapping("/detail/{id}")
    public Result findById(@PathVariable(value = "id" ) int id ){
        return goodsService.findById(id);
    }


}
