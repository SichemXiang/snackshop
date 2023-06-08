package com.snackshop.service;

import com.snackshop.entity.SsGoodsSales;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/5/29 2:45
 **/
public interface SsSalesService {

    List<SsGoodsSales> findSalesByTimeRange(int days);
}
