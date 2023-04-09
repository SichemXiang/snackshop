package com.snackshop.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * @Author: xsz
 * @Description: redis的工具类
 * @DateTime: 2023/3/13 0:36
 **/
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate ;

    //向redis中存值
    public boolean setValue(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            log.error("存入时异常-->{}",e.getMessage());
            return false;
        }
    }

    //向redis中指定过期时间
    public boolean setValuTime(String key,Object value,long time){
        try {
            if(time >0){
                redisTemplate.opsForValue().set(key,value,time, TimeUnit.MINUTES);
            }else {
                redisTemplate.opsForValue().set(key,value);
            }
            return true;
        }catch (Exception e){
            log.error("设置缓存并指定过期时间异常-->{}",e.getMessage());
            return false;
        }
    }

    //根据key获取redis中的值
    public Object getValue(String key){
        return key == null? null : redisTemplate.opsForValue().get(key);
    }

    //根据key删除redis中的缓存
    public void delKey(String... keys){
        if(keys != null && keys.length>0){
            if(keys.length == 1){
                redisTemplate.delete(keys[0]);
            }else {
                for (String key : keys) {
                    redisTemplate.delete(key);
                }
            }
        }
    }

    //判断是否存在
    public boolean haskey(String key){
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        }catch (Exception e){
            log.error("redis值不存在-->{}",e.getMessage());
            return false;
        }
    }

    //获取redis键过期时间
    //0代表永久有效
    //大于0就剩多少分钟失效
    public long isExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.MINUTES);
    }
    //给key加过期时间
    public boolean expire(String key, long time){
        try {
            if (time > 0){
                redisTemplate.expire(key,time,TimeUnit.MINUTES);
            }
            return true;
        }catch (Exception e){
            log.error("给旧的缓存设置新的过期时间异常-->{}",e.getMessage());
            return  false;
        }
    }

}
