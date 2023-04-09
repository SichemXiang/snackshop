package com.snackshop.entity;

import com.sun.scenario.effect.impl.prism.PrTexture;
import lombok.Data;

/**
 * @Author: xsz
 * @Description: 用户地址表
 * @DateTime: 2023/2/27 16:14
 **/
@Data
public class SsUserAddress {
    private int userAddressId;

    private String addresseeName;

    private String phone;

    private String province;

    private String city;

    private String distract;

    private String detail;

    private String createTime;

    private String userId;
}
