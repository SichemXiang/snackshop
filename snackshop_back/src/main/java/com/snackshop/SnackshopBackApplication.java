package com.snackshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan 用于扫描mapper文件
@MapperScan("com.snackshop.mapper")
@SpringBootApplication

public class SnackshopBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnackshopBackApplication.class, args);
    }

}
