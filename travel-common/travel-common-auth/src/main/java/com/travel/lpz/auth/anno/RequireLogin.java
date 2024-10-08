package com.travel.lpz.auth.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lpz
 * 标识方法，表示需要登录才能访问
 * @title RequireLogin
 * @date 2024/10/6 15:11
 * @description TODO
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {

}
