package com.travel.lpz.redis.key;

import java.util.concurrent.TimeUnit;
/*
redis key的通用规范
* */
public interface KeyPrefix {
    /*
    * 前缀
    * */
    String getPrefix();
    /*
    * 过期时间
    * */
    default Integer getTimeout(){
        return -1;
    }
    /*
    * 过期时间单位
    * */
    default TimeUnit getUnit(){
        return null;
    }

    //拼接前缀和key
    default String fullKey(String... suffix){
        StringBuffer sb = new StringBuffer();
        sb.append(getPrefix());
        for(String s : suffix){
            sb.append(":").append(s);
        }
        return sb.toString();
    }
}
