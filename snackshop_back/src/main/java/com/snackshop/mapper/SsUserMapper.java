package com.snackshop.mapper;


import com.snackshop.entity.SsUser;
import org.springframework.stereotype.Component;



/**
 * @Author: xsz
 * @Description: 用户相关的操作接口
 * @DateTime: 2023/2/24 15:55
 **/
@Component
public interface SsUserMapper {

    //根据用户名（phone）获取用户对象
    SsUser findByUsername(String username);

    //根据电话号码修改密码
    void updatePassword(String phone, String password);


}
