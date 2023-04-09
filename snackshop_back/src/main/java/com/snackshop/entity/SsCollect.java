package com.snackshop.entity;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: 收藏表
 * @DateTime: 2023/2/27 16:40
 **/

@Data
public class SsCollect {
    private int collectId;

    private int userId;

    private int goodsId;
}
