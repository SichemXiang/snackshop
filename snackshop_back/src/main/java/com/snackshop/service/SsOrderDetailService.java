package com.snackshop.service;


import com.snackshop.entity.SsOrderDetail;
import com.snackshop.util.Result;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:28
 **/
public interface SsOrderDetailService {

     Result findByOrderId(int id);

     void insert(SsOrderDetail orderDetail);
}
