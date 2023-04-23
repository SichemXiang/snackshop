package com.snackshop.entity;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/18 22:10
 **/
@Data
public class SsModifyPassword {
    private String phoneNumber;

    private String oldPassword;

    private String newPassword;

}
