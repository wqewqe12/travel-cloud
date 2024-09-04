package com.travel.lpz.user.service.controller;

import com.travel.lpz.UserInfoService;
import com.travel.lpz.user.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UseInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @GetMapping
    public List<UserInfo> list(){
        return userInfoService.list();
    }
}
