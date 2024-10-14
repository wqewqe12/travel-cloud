package com.travel.lpz.article.advice;

import com.travel.lpz.core.exception.BusinessException;
import com.travel.lpz.core.untils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//controller层的拦截，返回json的方式
@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public R<?> commonExceptionHandler(Exception e){
        log.error("[统一异常处理] 拦截到其他异常" , e);
        return R.defaultError();
    }

    @ExceptionHandler(BusinessException.class)
    public R<?> businessExceptionHandler(BusinessException e){
        log.debug("[统一异常处理] 拦截到业务异常" , e);
        log.warn("[统一异常处理] 拦截到业务异常, code={} ,msg={}", e.getCode() ,e.getMessage()) ;
        return R.error(e.getCode(),e.getMessage());
    }

}
