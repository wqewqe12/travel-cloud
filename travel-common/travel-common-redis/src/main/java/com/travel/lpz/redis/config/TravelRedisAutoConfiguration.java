package com.travel.lpz.redis.config;

import com.travel.lpz.redis.utils.RedisCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//不存在RedisConnectionFactory的情况下自动配置
@ConditionalOnMissingBean(RedisConnectionFactory.class)
@Configuration
public class TravelRedisAutoConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置序列化方式
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        return  redisTemplate;
    }

    @Bean
    public RedisCache redisCache(RedisConnectionFactory factory){
        return new RedisCache(redisTemplate(factory));
    }
}
