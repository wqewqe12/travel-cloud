package com.travel.lpz.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lpz
 * @title JwtProperties
 * @date 2024/10/6 14:40
 * @description TODO
 */


@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {
    private Integer expireTime;
    private String secret;
}
