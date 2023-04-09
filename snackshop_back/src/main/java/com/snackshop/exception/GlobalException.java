package com.snackshop.exception;

import com.snackshop.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/5 3:20
 **/

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public Result exception(RuntimeException e){
        e.printStackTrace();
        log.error("系统运行时异常--->{}",e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result exception(AccessDeniedException e){
        log.error("权限不足--->{}",e.getMessage());
        return Result.fail("权限不足，请联系管理员");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Result exception(UsernameNotFoundException e){
        log.error("用户名没有找到-->{}",e.getMessage());
        return Result.fail(e.getMessage());

    }
}

