package com.travel.lpz.auth.config;

//import com.travel.lpz.auth.interceptor.LoginInterceptor;
import com.travel.lpz.auth.interceptor.LoginInterceptor;
import com.travel.lpz.auth.untils.SpringContextUtil;
import com.travel.lpz.redis.utils.RedisCache;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
/**
 * @title TravelJwtAutoConfiguration
 * @date 2024/10/6 15:48
 * @description TODO
 */
@Configuration
@Import(WebConfig.class)
@EnableConfigurationProperties(JwtProperties.class)
public class TravelJwtAutoConfiguration {

    @Bean
    public LoginInterceptor loginInterceptor(RedisCache redisCache, JwtProperties jwtProperties) {
        return new LoginInterceptor(redisCache, jwtProperties);
    }

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }
}