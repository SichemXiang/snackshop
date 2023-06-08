package com.snackshop.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/27 16:40
 **/

@Data
public class SsGoods {
    //商品id
    @ApiModelProperty(value = "主键")
    private int goodsId;

    //商品名称
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    //商品对应的分类id
    @ApiModelProperty(value = "商品对应的分类id")
    private int categoryId;


    @ApiModelProperty(value = "商品对应的分类名称")
    private String categoryName;


    //商品对应的图片的id
    @ApiModelProperty(value = "商品对应图片")
    private String image;

    //商品信息
    @ApiModelProperty(value = "商品信息")
    private String goodsInfo;

    //商品的价格
    @ApiModelProperty(value = "商品的价格")
    private BigDecimal goodsPrice;

    //商品剩余的量
    @ApiModelProperty(value = "商品剩余的量")
    private int goodsTotal;

    //销量
    @ApiModelProperty(value = "销量")
    private int goodsSales;

    private boolean goodsHot = false;

    private boolean goodsSwiper = false;

    private int swiperSort;

}
