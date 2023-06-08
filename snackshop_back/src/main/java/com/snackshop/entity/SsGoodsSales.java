package com.snackshop.entity;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: 查询商品销量的实体
 * @DateTime: 2023/5/29 2:30
 **/
@Data
public class SsGoodsSales {

    private String goodsName;

    private int goodsSales;
}
