package com.snackshop.config.security;

import com.snackshop.entity.SsUser;
import com.snackshop.mapper.SsUserMapper;
import com.snackshop.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/4 4:34
 **/
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SsUserMapper ssUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    public MyUserDetailServiceImpl(SsUserMapper ssUserMapper) {
        this.ssUserMapper = ssUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在mapper中自定义登录，根据用户名 获取用户信息
        SsUser ssuser;
        if(redisUtil.haskey("userInfo_"+username)){
            //缓存中存在用户信息，直接从redis中取
            ssuser =  (SsUser)redisUtil.getValue("userInfo_"+username);
            redisUtil.expire("userInfo_"+username,30);
        }else {
            ssuser = ssUserMapper.findByUsername(username);
            if(ObjectUtils.isEmpty(ssuser)) {
                throw new UsernameNotFoundException("MyUserDetailServiceImpl:用户不存在");
            }
            redisUtil.setValuTime("userInfo_"+username,ssuser,30);
        }

        return ssuser;
    }



}
