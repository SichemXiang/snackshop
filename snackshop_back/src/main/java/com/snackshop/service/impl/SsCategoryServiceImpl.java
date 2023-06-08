package com.snackshop.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.snackshop.entity.SsCategory;
import com.snackshop.mapper.SsCategoryMapper;
import com.snackshop.service.SsCategoryService;
import com.snackshop.util.PageResult;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/16 6:17
 **/
@Service
@Slf4j
public class SsCategoryServiceImpl implements SsCategoryService {

    @Autowired
    private SsCategoryMapper categoryMapper;

    @Override
    public Result findPage(Queryinfo queryinfo) {
        log.info("开始商品数据分页-->页码{},-->页数{},-->查询内容{}",queryinfo.getPageNumber(),queryinfo.getPageSize(),queryinfo.getQueryString());
        PageHelper.startPage(queryinfo.getPageNumber(),queryinfo.getPageSize());
        Page<SsCategory> page = categoryMapper.findPage(queryinfo.getQueryString());
        long total = page.getTotal();
        List<SsCategory> result = page.getResult();
        log.info("查询的总条数-->{}",total);
        log.info("分页列表--{}",result);
        return new PageResult(total,result);
    }

    @Override
    public Result insert(SsCategory ssCategory) {
        categoryMapper.insert(ssCategory);
        return Result.success("添加成功");
    }

    @Override
    public Result delete(int id) {
        categoryMapper.delete(id);
        return Result.success("删除成功！");
    }

    @Override
    public Result update(SsCategory ssCategory) {
        categoryMapper.update(ssCategory);
        return Result.success("修改成功！");
    }

    @Override
    public List<SsCategory> findList() {
        return categoryMapper.findList();
    }

    @Override
    public String findCategoryNameById(int id) {
        return categoryMapper.findCategoryNameById(id);
    }


}
