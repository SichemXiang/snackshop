package com.snackshop.mapper;


import com.github.pagehelper.Page;
import com.snackshop.entity.SsDailyTransactionStats;
import com.snackshop.entity.SsOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 修改订单
     */
    void update(SsOrder order);


    List<SsOrder> findList();

    List<SsOrder> findByType(Integer type);

    /**
     * 修改订单状态
     */
    void updateStatus(SsOrder ssOrder);

    /**
     * 删除订单
     */
    void delete(int orderId);

    /**
     * 根据ordernum 返回 orderId
     */
    int selectOrderIdByOrderNum(String orderNsm);

    /**
     * 根据日期查询订单数和总收入
     */
    List<SsDailyTransactionStats> getDailyTransactionStats(int days);



}
