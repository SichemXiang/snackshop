package com.snackshop.service.impl;


import com.snackshop.entity.SsOrderDetail;
import com.snackshop.mapper.SsOrderDetailMapper;
import com.snackshop.service.SsOrderDetailService;
import com.snackshop.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:28
 **/
@Service
public class SsOrderDetailServiceImpl implements SsOrderDetailService {

    @Autowired
    private SsOrderDetailMapper orderDetailMapper;


    @Override
    public Result findByOrderId(int id) {
        return Result.success("查找成功！",orderDetailMapper.findByOrderId(id));
    }

    @Override
    public void insert(SsOrderDetail orderDetail) {
        orderDetailMapper.insert(orderDetail);
    }

    @Override
    public void deleteByOrderId(int orderId) {
        orderDetailMapper.deleteByOrderId(orderId);
    }
}
