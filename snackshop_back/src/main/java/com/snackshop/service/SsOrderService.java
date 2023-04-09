package com.snackshop.service;

import com.snackshop.entity.SsOrder;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:20
 **/
public interface SsOrderService{

    Result findOrder(Queryinfo queryinfo);

    void insert(SsOrder ssOrder);

    SsOrder findByOrderNum(String orderNum);

}
