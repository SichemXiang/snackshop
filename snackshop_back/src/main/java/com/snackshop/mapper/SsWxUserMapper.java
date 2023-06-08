package com.snackshop.mapper;

import com.github.pagehelper.Page;
import com.snackshop.entity.SsWxUser;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/22 2:47
 **/
public interface SsWxUserMapper {

    /**
     * 分页查询
     * @param queryString
     */
    Page<SsWxUser> findPage(String queryString);

    /**
     * 添加用户
     */
    void insert(SsWxUser wxUser);

    /**
     * 根据openid查找用户
     */
    SsWxUser findByOpenId(String openId);

    /**
     * 修改个人信息
     */
    void updateWxUserInfo(SsWxUser wxUser);

}
