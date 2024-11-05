package com.travel.lpz.auth.interceptor;

import com.travel.lpz.auth.anno.RequireLogin;
import com.travel.lpz.auth.config.JwtProperties;
import com.travel.lpz.auth.untils.AuthenticationUntils;
import com.travel.lpz.core.exception.BusinessException;
import com.travel.lpz.redis.utils.RedisCache;
import com.travel.lpz.user.redis.key.UserRedisKeyPrefix;
import com.travel.lpz.user.vo.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author lpz
 * @title LoginInterceptor
 * @date 2024/9/24 17:34
 * @description TODO
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisCache redisCache;
    private final JwtProperties jwtProperties;

    public LoginInterceptor(RedisCache redisCache, JwtProperties jwtProperties) {
        this.redisCache = redisCache;
        this.jwtProperties = jwtProperties;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断一个接口是否需要拦截
        //handlerMethod
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        //handler转换为handlerMethod
        HandlerMethod hm = (HandlerMethod) handler;
        //从handlerMethod对象中获取controller类
        Class<?> controllerClass = hm.getBeanType();
        RequireLogin classAnno = controllerClass.getAnnotation(RequireLogin.class);
        RequireLogin methodAnno = hm.getMethodAnnotation(RequireLogin.class);
        if (classAnno == null && methodAnno ==null){
            return true;
        }
        //从请求头中拿到jwt token
        String token = request.getHeader(LoginUser.TOKEN_HEADER);
        //jwt中解析token,失败抛异常
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token);
            Claims claims = jwt.getBody();
            //获取token中时间数据，与当前时间比较，判断是否过期
            String uuid = (String) claims.get(LoginUser.LOGIN_USER_REDIS_UUID);
            //从redis中获取token时间
            String userLoginKey = UserRedisKeyPrefix.USER_LOGIN_INFO_STRING.fullKey(uuid);
            LoginUser loginUser = redisCache.getCacheObject(userLoginKey);
            long loginTime;
            //为空则说明redis过期，token无效
            if (loginUser == null){
                throw new BusinessException(401,"token已失效");
            }
            //刷新过期时间
            else if (loginUser.getExpireTime()-(loginTime = System.currentTimeMillis()) <= LoginUser.TWENTY_MILLISECONDS){

                loginUser.setLoginTime(loginTime);
                long expireTime = loginTime + (jwtProperties.getExpireTime()*LoginUser.MINUTES_MILLISECONDS);
                loginUser.setExpireTime(expireTime);
                redisCache.setCacheObject(userLoginKey,loginUser,(int) expireTime, TimeUnit.MILLISECONDS);
            }
        }catch (Exception e){
            log.warn("[登录拦截] jwt token 解析失败", e);
            throw new BusinessException(401, "用户未认证");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //该拦截，在请求执行完成以后准备响应之前执行
        //线程即将完成，先将当前线程空间存储的数据清除掉
        AuthenticationUntils.removeUser();
    }
}

