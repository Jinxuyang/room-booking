package com.fehead.roomBooking.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Verge
 * @Date 2021/3/22 14:38
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.fehead.roomBooking.oauth2.mapper")
public class OAuth2Application {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2Application.class,args);
    }
}
