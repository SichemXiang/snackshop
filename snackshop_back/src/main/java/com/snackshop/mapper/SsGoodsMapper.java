package com.snackshop.mapper;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsGoods;
import com.snackshop.entity.SsGoodsSales;
import com.snackshop.entity.SsOrder;
import com.snackshop.entity.SsOrderDetail;

import java.util.List;
import java.util.Map;


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

    /**
     * 根据商品名字模糊查询
     */
    List<SsGoods> findByLike(String string);

    /**
     * 设置热卖
     */

    void updateHot(int goodsId,boolean goodsHot);

    /**
     *按照销量降序排列查找商品
     */
    List<SsGoods> findsGoodsBySales();

    /**
     * 设计轮播图
     */
    void updateSwiper(int goodsId,boolean goodsSwiper);

    /**
     * 更新商品的库存和销量
     */
    void updateGoodsTotal(int goodsId,int goodsTotal,int goodsSales);

    /**
     *根时间查询商品的销量
     */
    List<SsGoodsSales> findSalesByTimeRange(int days);

}
