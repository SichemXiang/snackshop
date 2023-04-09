package com.snackshop.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/2/24 23:02
 **/
@Data
@ApiModel(value = "响应参数")
public class Result implements Serializable {
    @ApiModelProperty(value = "是否成功表示",dataType = "boolean")
    private boolean flag;

    @ApiModelProperty(value = "响应信息",dataType = "string")
    private String message;

    @ApiModelProperty(value = "响应数据",dataType = "object")
    private Object data;

    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    //相应成功
    public static Result success(String message, Object data){
        return new Result(true,message,data);
    }

    //响应失败
    public static Result fail(String message){
        return new Result(false,message);
    }

    public static Result success(String message){
        return new Result(true,message);
    }

    public static Result success(Object data){
        return new Result(data);
    }
}
