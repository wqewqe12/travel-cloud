package com.travel.lpz.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lpz
 * @title TravelArticleApplication
 * @date 2024/10/8 20:20
 * @description TODO
 */
@SpringBootApplication
@MapperScan("com.travel.lpz.article.mapper")
public class TravelArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelArticleApplication.class, args);
    }
}
