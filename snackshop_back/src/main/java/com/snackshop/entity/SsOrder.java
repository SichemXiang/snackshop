package com.snackshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/27 16:15
 **/

@Data
public class SsOrder {
    private int orderId;

    private String orderNum;

    private String userId; // openId微信用户ID

    private BigDecimal totalPrice;

    private String orderAddress;

    private String consignee;

    private String phoneNumber;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date createTime;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date payTime;

    private Integer orderStatus=1;

    private SsOrderDetail[] goods;

}
