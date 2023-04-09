package com.snackshop.util;

import lombok.Data;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/24 23:12
 **/

@Data
public class Queryinfo  {
    //页码
    private int pageNumber;

    //总页数
    private int pageSize;

    //查询的内容
    private String queryString;

    public Queryinfo(int pageNumber,int pageSize){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

}
