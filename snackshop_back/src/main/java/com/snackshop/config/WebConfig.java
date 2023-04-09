package com.snackshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: xsz
 * @Description: 跨域处理
 * @DateTime: 2023/3/6 0:10
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                //允许访问的路径
                .addMapping("/**")
                .allowedOrigins("http://localhost:8888")
                .allowedMethods("GET","POST","DELETE","PUT")
                .allowCredentials(true)
                .maxAge(3600);

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/image/**").addResourceLocations("file:C:\\Users\\1972454587\\Desktop\\finally\\code\\images\\goodsImages\\");
    }



}
