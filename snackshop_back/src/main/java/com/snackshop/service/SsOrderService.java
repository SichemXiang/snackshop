package com.snackshop.service;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsDailyTransactionStats;
import com.snackshop.entity.SsOrder;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:20
 **/
public interface SsOrderService{

    Page<SsOrder> findOrder(Queryinfo queryinfo);

    void insert(SsOrder ssOrder);

    SsOrder findByOrderNum(String orderNum);

    void update(SsOrder ssOrder);

    List<SsOrder> findList();

    List<SsOrder>  findByType(Integer type);

    Result updateStatus(SsOrder ssOrder);

    void delete(int orderId);

    int selectOrderIdByOrderNum(String orderNum);

    List<SsDailyTransactionStats> getDailyTransactionStats(int days);
}
