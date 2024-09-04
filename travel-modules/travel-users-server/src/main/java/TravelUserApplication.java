package com.travel.lpz.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.travel.lpz.user.mapper")
@SpringBootApplication
public class TravelUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelUserApplication.class, args);
    }

}
