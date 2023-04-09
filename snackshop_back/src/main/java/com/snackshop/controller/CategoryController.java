package com.snackshop.controller;

import com.snackshop.entity.SsCategory;
import com.snackshop.service.SsCategoryService;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/16 6:21
 **/

@RestController
@RequestMapping("/category")
@Api(tags = "分类管理")
public class CategoryController {

    @Autowired
    private SsCategoryService categoryService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/")
    public Result findPage(@RequestBody Queryinfo queryinfo){
        return categoryService.findPage(queryinfo);
    }

    @ApiOperation(value = "添加分类")
    @PostMapping("/insert")
    public Result insert(@RequestBody SsCategory ssCategory){
        return  categoryService.insert(ssCategory);
    }

    @ApiOperation(value = "删除分类")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable(value = "id") int id){
        return categoryService.delete(id);
    }

    @ApiOperation(value = "更新分类")
    @PostMapping("/update")
    public Result update(@RequestBody SsCategory ssCategory){
        return categoryService.update(ssCategory);
    }



}
