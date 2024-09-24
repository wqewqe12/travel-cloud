package com.travel.lpz.user.redis.key;

import com.travel.lpz.redis.key.BeanKeyPrefix;

import java.util.concurrent.TimeUnit;

public class UserRedisKeyPrefix extends BeanKeyPrefix {
    public static final UserRedisKeyPrefix USER_REGISTER_VERIFY_CODE_STRING =
            new UserRedisKeyPrefix("USERS:REGISTER:VERIFY_CODE:",30,TimeUnit.MINUTES);
    public UserRedisKeyPrefix(String prefix) {
        super(prefix);
    }

    public UserRedisKeyPrefix(String prefix, Integer timeout, TimeUnit unit) {
        super(prefix, timeout, unit);
    }
}
