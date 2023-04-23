package com.snackshop.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.snackshop.entity.SsGoods;
import com.snackshop.mapper.SsGoodsMapper;
import com.snackshop.service.SsGoodsService;
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
 * @DateTime: 2023/3/14 0:48
 **/
@Service
@Slf4j
public class SsGoodsServiceImpl implements SsGoodsService {

    @Autowired
    private SsGoodsMapper ssGoodsMapper;

    @Override
    public Result findPage(Queryinfo queryinfo) {
        log.info("开始商品数据分页-->页码{},-->页数{},-->查询内容{}",queryinfo.getPageNumber(),queryinfo.getPageSize(),queryinfo.getQueryString());
        PageHelper.startPage(queryinfo.getPageNumber(),queryinfo.getPageSize());
        Page<SsGoods> page = ssGoodsMapper.findPage(queryinfo.getQueryString());
        long total = page.getTotal();
        List<SsGoods> result = page.getResult();
        long pageNum = page.getPages();
        log.info("查询的总条数-->{}",total);
        log.info("分页列表--{}",result);
        return new PageResult(total,result);
    }

    @Override
    public Result insert(SsGoods ssGoods) {
        ssGoodsMapper.insert(ssGoods);
        return  Result.success("添加商品成功！");
    }

    @Override
    public Result delete(int id) {
        ssGoodsMapper.delete(id);
        return Result.success("删除商品成功！");
    }

    @Override
    public Result update(SsGoods ssGoods) {
        ssGoodsMapper.update(ssGoods);
        return Result.success("修改商品信息成功！");
    }

    @Override
    public Result findById(int id) {
        return Result.success("查询成功",ssGoodsMapper.findById(id));
    }

    @Override
    public Result findSwiper() {
        return Result.success("轮播图查询成功！",ssGoodsMapper.findSwiper());
    }

    @Override
    public Result findHot() {
        return Result.success("热卖查询成功！",ssGoodsMapper.findHot());
    }


    @Override
    public List<SsGoods> findByCategoryId(int id) {
        return ssGoodsMapper.findByCategoryId(id);
    }

    @Override
    public List<SsGoods> findByLike(String string) {
        return ssGoodsMapper.findByLike(string);
    }

    @Override
    public Result updateHot(int id, boolean hot) {
        ssGoodsMapper.updateHot(id,hot);
        return Result.success("设置热卖成功！");
    }

    @Override
    public Result updateSwiper(int id, boolean swiper) {
        ssGoodsMapper.updateSwiper(id,swiper);
        return Result.success("设置轮播图成功！");
    }


}
