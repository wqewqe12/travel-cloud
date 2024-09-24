package com.travel.lpz.user.interceptor;

import com.travel.lpz.core.exception.BusinessException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bxc
 * @title LoginInterceptor
 * @date 2024/9/24 17:34
 * @description TODO
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中拿到jwt token
        String token = request.getHeader("token");
        //jwt中解析token,失败抛异常
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .setSigningKey("travel")
                    .parseClaimsJws(token);
            Claims claims = jwt.getBody();
            //获取token中时间数据，与当前时间比较，判断是否过期
            Long loginTime =(Long) claims.get("loginTime");
            Integer expireTime = (Integer) claims.get("expireTime");
            long current = System.currentTimeMillis();
            long min = (current-loginTime)/1000/60;
            if (min > expireTime){
                throw new BusinessException(401,"token已失效");
            }
        }catch (Exception e){
            throw new BusinessException("登录信息已失效");
        }
        return true;
    }
}
