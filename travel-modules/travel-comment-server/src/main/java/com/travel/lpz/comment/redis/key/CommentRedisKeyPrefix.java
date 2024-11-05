package com.travel.lpz.comment.redis.key;

import com.travel.lpz.redis.key.BeanKeyPrefix;

import java.util.concurrent.TimeUnit;

public class CommentRedisKeyPrefix extends BeanKeyPrefix {
    public static final CommentRedisKeyPrefix STRATEGIES_STAT_DATA_MAP=
            new CommentRedisKeyPrefix("STRATEGIES_STAT_DATA");

    public CommentRedisKeyPrefix(String prefix) {
        super(prefix);
    }

    public CommentRedisKeyPrefix(String prefix, Integer timeout, TimeUnit unit) {
        super(prefix, timeout, unit);
    }
}
