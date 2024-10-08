package com.travel.lpz.user.controller;

import com.travel.lpz.auth.anno.RequireLogin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bxc
 * @title TestController
 * @date 2024/10/8 16:30
 * @description TODO
 */
@RestController
@RequestMapping("/test1")
public class TestController {
    @GetMapping("/aaa")
    public String test1(){
        return "test1:aaa";
    }
    @RequireLogin
    @GetMapping("/bbb")
    public String test2(){
        return "test1:bbb";
    }

}
