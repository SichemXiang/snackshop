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
    /**
     * 分页查询
     * @param queryinfo 页码，页数，查询类容
     */
    Result findPage(Queryinfo queryinfo);

    Result insert(SsGoods ssGoods);

    Result delete(int id);

    Result update(SsGoods ssGoods);

    Result findById(int id);

    Result findSwiper();

    Result findHot();

    List<SsGoods> findByCategoryId(int id);

    List<SsGoods> findByLike(String string);

    Result updateHot(int id,boolean hot);

    Result updateSwiper(int id,boolean swiper);



}
