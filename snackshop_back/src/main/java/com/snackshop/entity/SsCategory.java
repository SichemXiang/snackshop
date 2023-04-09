package com.snackshop.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/27 16:40
 **/

@Data
public class SsCategory {
    @ApiModelProperty(value = "主键")
    private int categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "排序")
    //分类排序
    private int categorySort;


    private List<SsGoods> goodsList;


}

