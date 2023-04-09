package com.snackshop.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 0:54
 **/
@Data
public class SsOrderDetail {
    private int orderDetailId;

    private int orderId;

    private int goodsId;

    private int goodsNumber;

    private BigDecimal goodsPrice;

    private String goodsName;

    private String goodsImage;
}
