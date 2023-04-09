package com.snackshop.mapper;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:06
 **/
@Mapper
public interface SsOrderMapper {
    /**
     * 分页查询
     */
    Page<SsOrder> findOrder(String queryString);

    /**
     * 添加订单
     */
    void insert(SsOrder order);

    /**
     * 根据订单号查询订单
     */
    SsOrder findByOrderNum(String orderNum);

}
