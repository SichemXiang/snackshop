package com.snackshop.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/18 2:20
 **/

@Data
public class SsWxUser {

    private int id;

    private String phone;

    private String openId;

    private String wxNickName;

    private String wxAvatarUrl;

    private boolean wxStatus = true;


    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date registerTime; // 注册日期

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date lastLoginTime; // 最后登录日期



}
