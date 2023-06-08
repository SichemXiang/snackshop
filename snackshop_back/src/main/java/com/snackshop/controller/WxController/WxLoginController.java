package com.snackshop.controller.WxController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.snackshop.entity.SsWxUser;
import com.snackshop.service.SsWxUserService;
import com.snackshop.util.RedisUtil;
import com.snackshop.util.Result;
import com.snackshop.util.StringUtils;
import com.snackshop.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xsz
 * @Description: TODO
 * @DateTime: 2023/3/26 2:28
 **/

@Api(tags = "小程序登录")
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxLoginController {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Autowired
    private SsWxUserService wxUserService;

    private final TokenUtil tokenUtil;

    public WxLoginController(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public Result login(String code) throws IOException {
        if(StringUtils.isEmpty(code)){
            return Result.fail("登陆失败");
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appid +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=authorization_code";

        //创建一个client请求
        CloseableHttpClient client = HttpClientBuilder.create().build();
        //构建get请求
        HttpGet get = new HttpGet(url);
        //发送请求
        CloseableHttpResponse response = client.execute(get);
        log.info("请求响应码：{}",response.getStatusLine().getStatusCode());
        String result = EntityUtils.toString(response.getEntity());
        log.info("请求响应的结果--->{}",result);
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = jsonObject.getString("openid");
        log.info("微信小程序唯一标识openid：{}",openid);
        SsWxUser wxUser;
        wxUser= wxUserService.findByOpenId(openid);

        //查询数据库，如果存在，登录，不存再则插入新用户
        if (wxUser == null){
            log.info("小程序用户不存在，插入用户");
            SsWxUser user = new SsWxUser();
            user.setOpenId(openid);
            wxUserService.insert(user);
        }

        String avatar = wxUser.getWxAvatarUrl();
        String nickName = wxUser.getWxNickName();

        redisUtil.setValuTime("userInfo_"+openid,wxUser.toString(),30);

        String token = tokenUtil.generateWxToken(openid);
        Map<String,Object> map = new HashMap<>(2);
        Map<String,String> userInfoMap = new HashMap<>();
        userInfoMap.put("avatar",avatar);
        userInfoMap.put("nickName",nickName);
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        map.put("userInfo",userInfoMap);

        return Result.success("登陆成功！",map);

    }


    @ApiOperation(value = "openid测试")
    @GetMapping("/{openid}")
    public SsWxUser openid(@PathVariable(value = "openid") String openid){
        return wxUserService.findByOpenId(openid);
    }
}
