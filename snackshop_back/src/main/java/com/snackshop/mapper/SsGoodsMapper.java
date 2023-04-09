package com.snackshop.mapper;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsGoods;

import java.util.List;


/**
 * @Author: xsz
 * @Description: 商品的crud
 * @DateTime: 2023/3/13 7:26
 **/
public interface SsGoodsMapper {
    /**
     * 添加商品
     */
    void insert(SsGoods ssGoods);

    /**
     * 修改商品信息
     */
    void update(SsGoods ssGoods);

    /**
     * 删除商品
     */
    void delete(int id);

    /**
     * 分页查询
     */
    Page<SsGoods> findPage(String queryString);


    /**
     * 根据id查询
     */
    SsGoods findById(int id);

    /**
     * 查询轮播图
     */
    List<SsGoods> findSwiper();

    /**
     * 查询热销
     */
    List<SsGoods> findHot();

    /**
     * 小程序端根据分类id查询商品
     */
    List<SsGoods> findByCategoryId(int id);


}
