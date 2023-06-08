package com.snackshop.service;

import com.snackshop.entity.SsGoods;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/14 0:45
 **/
public interface SsGoodsService {
    //分页查询
    Result findPage(Queryinfo queryinfo);

    Result insert(SsGoods ssGoods);

    Result delete(int id);

    Result update(SsGoods ssGoods);

    Result findById(int id);

    //根据id查询商品，返回的是goods类型
    SsGoods findGoodsByid(int id);

    Result findSwiper();

    Result findHot();

    List<SsGoods> findByCategoryId(int id);

    List<SsGoods> findByLike(String string);

    Result updateHot(int id,boolean hot);

    Result updateSwiper(int id,boolean swiper);

    void updateGoodsTotal(int goodsId,int goodsTotal,int goodsSales);

    List<SsGoods> findsGoodsBySales();

    void changeHotToDefault();

}
