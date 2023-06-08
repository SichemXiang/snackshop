package com.snackshop.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.snackshop.entity.SsDailyTransactionStats;
import com.snackshop.entity.SsOrder;
import com.snackshop.mapper.SsOrderMapper;
import com.snackshop.service.SsOrderService;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;
import lombok.extern.slf4j.Slf4j;
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

    private final SsOrderMapper ssOrderMapper;

    public SsOrderServiceImpl(SsOrderMapper ssOrderMapper) {
        this.ssOrderMapper = ssOrderMapper;
    }

    @Override
    public Page<SsOrder> findOrder(Queryinfo queryinfo) {
        PageHelper.startPage(queryinfo.getPageNumber(),queryinfo.getPageSize());
        Page<SsOrder> page = ssOrderMapper.findOrder(queryinfo.getQueryString());
        return  page;
    }

    @Override
    public void insert(SsOrder ssOrder) {
        ssOrderMapper.insert(ssOrder);
    }

    @Override
    public SsOrder findByOrderNum(String orderNum) {
        return ssOrderMapper.findByOrderNum(orderNum);
    }

    @Override
    public void update(SsOrder ssOrder) {
        ssOrderMapper.update(ssOrder);
    }

    @Override
    public List<SsOrder> findList() {
        return ssOrderMapper.findList();
    }

    @Override
    public List<SsOrder> findByType(Integer type) {
        return ssOrderMapper.findByType(type);
    }

    @Override
    public Result updateStatus(SsOrder ssOrder) {
        ssOrderMapper.updateStatus(ssOrder);
        return Result.success("修改成功！");
    }

    @Override
    public void delete(int orderId) {
        ssOrderMapper.delete(orderId);
    }


    @Override
    public int selectOrderIdByOrderNum(String orderNum) {
        return ssOrderMapper.selectOrderIdByOrderNum(orderNum);
    }

    @Override
    public List<SsDailyTransactionStats> getDailyTransactionStats(int days) {
        return ssOrderMapper.getDailyTransactionStats(days);
    }


}
