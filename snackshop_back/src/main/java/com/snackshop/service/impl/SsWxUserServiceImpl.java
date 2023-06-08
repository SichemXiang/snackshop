package com.snackshop.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.snackshop.entity.SsWxUser;
import com.snackshop.mapper.SsWxUserMapper;
import com.snackshop.service.SsWxUserService;
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
 * @DateTime: 2023/3/22 3:14
 **/
@Service
@Slf4j
public class SsWxUserServiceImpl implements SsWxUserService {

    @Autowired
    private SsWxUserMapper wxUserMapper;

    @Override
    public Result findPage(Queryinfo queryinfo) {
        PageHelper.startPage(queryinfo.getPageNumber(),queryinfo.getPageSize());
        Page<SsWxUser> page = wxUserMapper.findPage(queryinfo.getQueryString());
        long total = page.getTotal();
        List<SsWxUser> result = page.getResult();
        log.info("查询的总条数-->{}",total);
        log.info("分页列表--{}",result);
        return new PageResult(total,result);
    }

    @Override
    public Result insert(SsWxUser wxUser) {
        wxUserMapper.insert(wxUser);
        return Result.success("添加成功！");
    }

    @Override
    public SsWxUser findByOpenId(String openId) {
        return wxUserMapper.findByOpenId(openId);
    }

    @Override
    public void updateWxUserInfo(SsWxUser wxUser) {
        wxUserMapper.updateWxUserInfo(wxUser);
    }
}
