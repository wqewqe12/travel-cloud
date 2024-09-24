package com.travel.lpz.redis.key;

import lombok.Setter;

import java.util.concurrent.TimeUnit;
@Setter
public class BeanKeyPrefix implements KeyPrefix {
    private String prefix;
    private Integer timeout;
    private TimeUnit unit;
    public BeanKeyPrefix(String prefix){
        this(prefix , -1 ,null);
    }

    public BeanKeyPrefix(String prefix,Integer timeout ,TimeUnit unit) {
        this.prefix = prefix;
        this.timeout = timeout;
        this.unit = unit;
    }


    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public Integer getTimeout() {
        return timeout;
    }

    @Override
    public TimeUnit getUnit() {
        return unit;
    }
}
