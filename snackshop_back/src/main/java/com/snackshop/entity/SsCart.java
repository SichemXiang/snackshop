package com.snackshop.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/27 16:41
 **/

@Data
public class SsCart {
    private int cartId;

    private int userId;

    private List<SsGoods> goodsId;
    //购物车商品的的数量
    private int buyQuantity;

}
