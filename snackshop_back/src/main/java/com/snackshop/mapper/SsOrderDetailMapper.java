package com.snackshop.mapper;

import com.snackshop.entity.SsOrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:04
 **/
@Mapper
public interface SsOrderDetailMapper {
    /**
     * 根据订单id查询订单detail
     */
    List<SsOrderDetail> findByOrderId(int id);

    void insert(SsOrderDetail orderDetail);

    /**
     * 根据orderId删除orderDetail
     */
    void deleteByOrderId(int orderId);


}
