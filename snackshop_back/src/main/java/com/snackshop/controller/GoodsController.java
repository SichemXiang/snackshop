package com.snackshop.controller;

import com.snackshop.entity.SsGoods;
import com.snackshop.service.SsGoodsService;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xsz
 * @Description: 商品数据控制层
 * @DateTime: 2023/3/14 1:40
 **/
@RestController
@Api(tags = "商品管理")
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private SsGoodsService goodsService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody Queryinfo queryinfo){
        return goodsService.findPage(queryinfo);
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/insert")
    public Result insert(@RequestBody SsGoods ssGoods){
        return goodsService.insert(ssGoods);
    }

    @ApiOperation(value = "修改商品信息")
    @PostMapping("/update")
    public Result update(@RequestBody SsGoods ssGoods){
        return goodsService.update(ssGoods);
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/delete/{id}")
    public Result delete( @PathVariable(value = "id") int id){
        return goodsService.delete(id);
    }


}
