package com.snackshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author: xsz
 * @Description: 用户实体
 * @DateTime: 2023/2/24 15:56
 **/

@Data
public class SsUser implements UserDetails {
    private int userId;

    //电话号码，即登陆账号
    private String phone;

    private String password;

    //网名
    private String nickName;

    private String avatar;

    //前端展示的头像
    private String frontAvatar;

    private boolean userStatus;




    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_17843815004"));
        return list;
    }

    //用户名
    @Override
    @JsonIgnore
    public String getUsername() {
        return phone;
    }

    //账号是否过期
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    //账号是否被锁定
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }


    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    //是否被禁用
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return userStatus;
    }
}
