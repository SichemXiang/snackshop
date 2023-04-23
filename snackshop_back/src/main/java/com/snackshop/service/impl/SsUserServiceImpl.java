package com.snackshop.service.impl;

import com.snackshop.config.security.MyUserDetailServiceImpl;
import com.snackshop.entity.SsUser;
import com.snackshop.mapper.SsUserMapper;
import com.snackshop.service.SsUserService;
import com.snackshop.util.TokenUtil;
import com.snackshop.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.snackshop.util.Result;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/24 16:36
 **/

@Service
@Slf4j
public class SsUserServiceImpl implements SsUserService {

    private final SsUserMapper ssUserMapper;

    private final MyUserDetailServiceImpl userDetailsService;

    private final TokenUtil tokenUtil;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    public SsUserServiceImpl(SsUserMapper ssUserMapper, MyUserDetailServiceImpl userDetailsService, TokenUtil tokenUtil, PasswordEncoder passwordEncoder) {
        this.ssUserMapper = ssUserMapper;
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    //登录接口
    @Override
    public Result login(LoginVo loginVo) {
        log.info("开始登陆");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        log.info("判断用户是否存在，密码是否正确");
        if(null == userDetails || !passwordEncoder.matches(loginVo.getPassword(),userDetails.getPassword() )){
            return Result.fail("账号或密码错误，请重新输入");
        }
        if(!userDetails.isEnabled()){
            return Result.fail("该账号已禁用");
        }
        log.info("登陆成功，在security中存入登陆这信息");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登录信息获取token");
        //需要借助jwt生成token
        String token= tokenUtil.generateToken(userDetails);
        Map<String,String> map = new HashMap<>(2);
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        return Result.success("登录成功",map);

    }

    @Override
    public SsUser loadUserByUsername(String username) {
        return ssUserMapper.findByUsername(username);
    }

    /**
     * 修改密码
     * @param phone 电话
     * @param password 新密嘛
     * @return result
     */
    @Override
    public Result updatePassword(String phone, String password) {
        ssUserMapper.updatePassword(phone,password);
        return Result.success("修改成功");
    }
}

