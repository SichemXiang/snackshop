package com.snackshop.entity;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: 订单的地址
 * @DateTime: 2023/2/27 16:15
 **/

@Data
public class SsOrderAddress {
    private int orderAddressId;

    private String addresseeName;

    private String phone;

    private String province;

    private String city;

    private String distract;

    private String detail;

    private int orderId;

    private int userId;

    private String createTime;

}
