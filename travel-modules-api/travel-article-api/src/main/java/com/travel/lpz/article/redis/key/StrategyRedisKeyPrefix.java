package com.travel.lpz.comment.key;

import com.travel.lpz.redis.key.BeanKeyPrefix;

import java.util.concurrent.TimeUnit;

public class StrategyRedisKeyPrefix extends BeanKeyPrefix {
    public static final StrategyRedisKeyPrefix STRATEGIES_STAT_DATA_MAP=
            new StrategyRedisKeyPrefix("STRATEGIES_STAT_DATA");


    public StrategyRedisKeyPrefix(String prefix) {
        super(prefix);
    }

    public StrategyRedisKeyPrefix(String prefix, Integer timeout, TimeUnit unit) {
        super(prefix, timeout, unit);
    }
}
