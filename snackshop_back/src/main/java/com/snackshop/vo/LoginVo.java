package com.snackshop.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/1 15:49
 **/

@Data
@ApiModel(value = "登陆参数")
public class LoginVo {

    @ApiModelProperty(value = "用户名",dataType = "string")
    private String username;

    @ApiModelProperty(value = "密码",dataType = "string")
    private String password;
}
