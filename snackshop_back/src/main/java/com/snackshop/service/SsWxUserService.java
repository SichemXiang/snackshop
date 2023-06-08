package com.snackshop.service;

import com.snackshop.entity.SsWxUser;
import com.snackshop.util.Queryinfo;
import com.snackshop.util.Result;

import javax.annotation.Resource;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/22 3:13
 **/
@Resource
public interface SsWxUserService {

    Result findPage(Queryinfo queryinfo);

    Result insert(SsWxUser wxUser);

    SsWxUser findByOpenId(String openId);

    void updateWxUserInfo(SsWxUser wxUser);
}
