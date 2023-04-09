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

    private String userAddress;


}
