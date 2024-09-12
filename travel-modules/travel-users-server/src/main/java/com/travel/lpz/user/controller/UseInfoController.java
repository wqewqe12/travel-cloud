package com.travel.lpz.user.controller;

import com.travel.lpz.core.untils.R;
import com.travel.lpz.user.service.UserInfoService;
import com.travel.lpz.user.user.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UseInfoController {
    private final UserInfoService userInfoService;

    public UseInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

//    @Autowired
//    private UserInfoService userInfoService;
    @GetMapping
    public List<UserInfo> list(){
        return userInfoService.list();
    }

    @GetMapping("/phone/exists")
    public R<Boolean> checkPhone(String phone){
        return R.success(userInfoService.findByPhone(phone) != null);
    }


}
