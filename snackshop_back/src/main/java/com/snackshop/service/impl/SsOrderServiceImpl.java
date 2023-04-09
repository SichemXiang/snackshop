package com.snackshop.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.snackshop.entity.SsOrder;
import com.snackshop.mapper.SsOrderMapper;
import com.snackshop.service.SsOrderService;
import com.snackshop.util.PageResult;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/4/9 1:22
 **/
@Service
@Slf4j
public class SsOrderServiceImpl implements SsOrderService {

    @Autowired
    private SsOrderMapper ssOrderMapper;

    @Override
    public Result findOrder(Queryinfo queryinfo) {
        log.info("开始订单数据分页-->页码{},-->页数{},-->查询内容{}",queryinfo.getPageNumber(),queryinfo.getPageSize(),queryinfo.getQueryString());
        PageHelper.startPage(queryinfo.getPageNumber(),queryinfo.getPageSize());
        Page<SsOrder> page = ssOrderMapper.findOrder(queryinfo.getQueryString());
        long total = page.getTotal();
        List<SsOrder> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public void insert(SsOrder ssOrder) {
        ssOrderMapper.insert(ssOrder);
    }

    @Override
    public SsOrder findByOrderNum(String orderNum) {
        return ssOrderMapper.findByOrderNum(orderNum);
    }
}
