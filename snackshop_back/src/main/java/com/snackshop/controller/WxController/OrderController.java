package com.snackshop.controller.WxController;


import com.snackshop.entity.SsOrder;
import com.snackshop.entity.SsOrderDetail;
import com.snackshop.properties.WeixinpayProperties;
import com.snackshop.service.SsOrderDetailService;
import com.snackshop.service.SsOrderService;
import com.snackshop.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xsz
 * @Description: 订单控制器
 * @DateTime: 2023/4/6 7:21
 **/

@RestController
@Api(tags = "创建订单")
@RequestMapping("/my/order")
public class OrderController {

    @Autowired
    private SsOrderDetailService orderDetailService;

    @Autowired
    private SsOrderService orderService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private WeixinpayProperties weixinpayProperties;


    @ApiOperation(value = "创建订单")
    @PostMapping("/create")
    public Result createOrder(@RequestBody SsOrder order, @RequestHeader (value = "token") String token){
        System.out.println("token===="+token);
        System.out.println("ssorder====="+order);
        System.out.println("openid====="+tokenUtil.getOpenid(token));
        order.setUserId(tokenUtil.getOpenid(token));
        //添加订单到数据库
        order.setOrderNum("snackshop"+ DateUtil.getCurrentDateStr());
        order.setCreateDate(new Date());
        SsOrderDetail[] goods = order.getGoods();
        orderService.insert(order);
        Arrays.stream(goods).forEach(orderDetail -> {
            orderDetail.setOrderId(order.getOrderId());
            orderDetailService.insert(orderDetail);
        });

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("orderNumber",order.getOrderNum());
        return Result.success("订单成功",resultMap);

    }

    @PostMapping("/preparePay")
    public Result preparePay(@RequestBody String orderNum) throws Exception {
        System.out.println("ordernum===="+orderNum);
        SsOrder order = orderService.findByOrderNum(orderNum);

        System.out.println("appid="+weixinpayProperties.getAppid());
        System.out.println("mch_id="+weixinpayProperties.getMch_id());
        System.out.println("nonce_str="+ StringUtils.getRandomString(32));

        System.out.println("body="+"snackshop购买测试");
        System.out.println("out_trade_no="+orderNum);
        System.out.println("total_fee="+order.getTotalPrice().movePointRight(2));
        System.out.println("spbill_create_ip="+"127.0.0.1");
        System.out.println("notify_url="+weixinpayProperties.getNotify_url());
        System.out.println("trade_type="+"JSAPI");
        System.out.println("openid="+order.getUserId());
        System.out.println("sign=");

        Map<String,Object> map=new HashMap<>();
        map.put("appid",weixinpayProperties.getAppid());
        map.put("mch_id",weixinpayProperties.getMch_id());
        map.put("nonce_str",StringUtils.getRandomString(32));
        map.put("body","snackshop购买测试");
        map.put("out_trade_no",orderNum);
        // map.put("total_fee",order.getTotalPrice().movePointRight(2));
        map.put("total_fee",1);
        map.put("spbill_create_ip","127.0.0.1");
        map.put("notify_url",weixinpayProperties.getNotify_url());
        map.put("trade_type","JSAPI");
        map.put("openid",order.getUserId());
        map.put("sign",getSign(map));

        // 参数转成xml
        String xml = XmlUtil.genXml(map);
        System.out.println("xml="+xml);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(weixinpayProperties.getUrl().toString());
        StringEntity entity = new StringEntity(xml);
        httppost.setEntity(entity);
        httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
        HttpResponse response = httpClient.execute(httppost);

        String httpEntityContent = getHttpEntityContent(response);
        System.out.println(httpEntityContent);


        return Result.success("ok");
    }

    /**
     * 微信支付签名算法sign
     */
    private String getSign(Map<String,Object> map) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
        Arrays.sort(keyArr);//对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + weixinpayProperties.getKey());
        String sign = string2MD5(sb.toString());
        System.out.println("sign="+sign);
        return sign;
    }

    /***
     * MD5加码 生成32位md5码
     */
    private String string2MD5(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            return sb.toString();
        }
        return "";
    }


}
