package com.snackshop.service;


import com.snackshop.entity.SsUser;
import com.snackshop.util.Result;
import com.snackshop.vo.LoginVo;

import javax.annotation.Resource;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/24 16:35
 **/
@Resource
public interface SsUserService {

  //登陆接口
  Result login(LoginVo loginVo);

  //根据用户名（phone）获取用户对象
  SsUser loadUserByUsername(String username);
}
