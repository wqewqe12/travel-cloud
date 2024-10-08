package com.travel.lpz.auth.config;


import com.travel.lpz.auth.interceptor.LoginInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author bxc
 * @title WebConfig
 * @date 2024/9/24 17:50
 * @description TODO
 */
@Configuration
@Import(WebConfig.class)
@ConditionalOnMissingBean(WebMvcConfigurer.class)
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/users/login","/users/register")
                .addPathPatterns("/**");

    }

}
