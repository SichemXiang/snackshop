package com.snackshop.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: xsz
 * @Description: 字符串判断
 * @DateTime: 2023/3/26 2:32
 **/
public class StringUtils {

    //判断不是空字符串
    public static boolean isNotEmpty(String str){
        return str !=null && !"".equals(str);
    }


    //判断是空字符串
    public static boolean isEmpty(String str){
        return str ==null || "".equals(str);
    }


    //格式化模糊查询
    public static String formatLike(String str){
        if(isNotEmpty(str)){
            return "%"+str+"%";
        }else{
            return null;
        }
    }

    public static List<String> filterWhite(List<String> list){
        List<String> resultList=new ArrayList<String>();
        for(String l:list){
            if(isNotEmpty(l)){
                resultList.add(l);
            }
        }
        return resultList;
    }


    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 去掉空格
        content = content.replaceAll(" ", "");
        return content;
    }


    public static String genSixRandomNum(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++)
        {
            result+=random.nextInt(10);
        }
        return result;
    }


    public static String getRandomString(int length){
        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i){
            int number = random.nextInt(2);
            long result = 0;

            switch(number){
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }


}
