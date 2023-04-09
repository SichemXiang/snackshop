package com.snackshop.controller.WxController;

import com.snackshop.entity.SsCategory;
import com.snackshop.entity.SsGoods;
import com.snackshop.service.SsCategoryService;
import com.snackshop.service.SsGoodsService;
import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/27 1:23
 **/


@RestController
@Api(tags = "小程序分类查询商品")
@RequestMapping("/wxCategory")
@Slf4j
public class WxCategoryController {

    @Autowired
    private SsCategoryService categoryService;

    @Autowired
    private SsGoodsService goodsService;

    /**
     * 查询所有分类，包括分类的goodsList
     * @return
     */
    @ApiOperation(value = "分类查询商品")
    @GetMapping("/findCategories")
    public Result findCategories(){
        List<SsCategory> categoryList= categoryService.findList();
        for(SsCategory ssCategory:categoryList){
            List<SsGoods> goodsList =  goodsService.findByCategoryId(ssCategory.getCategoryId());
            ssCategory.setGoodsList(goodsList);
        }

        return Result.success("分类查询商品成功！",categoryList);
    }



}
