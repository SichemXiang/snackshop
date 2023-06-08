package com.snackshop.service.impl;

import com.snackshop.entity.SsGoodsSales;
import com.snackshop.mapper.SsGoodsMapper;
import com.snackshop.service.SsSalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/5/29 2:45
 **/
@Service
@Slf4j
public class SsSalesServiceImpl implements SsSalesService {

    @Autowired
    private SsGoodsMapper goodsMapper;

    @Override
    public List<SsGoodsSales> findSalesByTimeRange(int days) {
        return  goodsMapper.findSalesByTimeRange(days);
    }
}
