package com.travel.lpz.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;
    public Long increment(String key , int incr){
        return redisTemplate.opsForValue().increment(key,incr);
    }

    /*
    * 缓存基本的对象，Integet,String等等
    * key缓存的键值
    * value缓存的值
    */
    public <T> void setCacheObject (final String key ,  final T value){
         redisTemplate.opsForValue().set(key,value);
    }
    /*
     * 缓存基本的对象，Integet,String等等
     * key缓存的键值
     * value缓存的值
     * timeout时间
     * timeUnit时间颗粒度
     */
    public <T> void setCacheObject (final String key , final T value , final Integer timeout , final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value , timeout ,timeUnit);
    }

    public boolean expire(final String key , final Long timeout ){
        return expire(key,timeout,TimeUnit.)
    }




}