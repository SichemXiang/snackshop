package com.snackshop.entity;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/5/29 8:35
 **/
@Data
public class SsDailyTransactionStats {

    private String transactionDate;

    private int totalOrders;

    private double dailyTotalPrice;
}
