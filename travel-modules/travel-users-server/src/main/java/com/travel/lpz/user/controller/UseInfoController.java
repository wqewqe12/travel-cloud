package com.travel.lpz.user.controller;

import com.travel.lpz.core.untils.R;
import com.travel.lpz.user.dto.UserInfoDTO;
import com.travel.lpz.user.service.UserInfoService;
import com.travel.lpz.user.domain.UserInfo;
import com.travel.lpz.user.vo.RegisterRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UseInfoController {
    private final UserInfoService userInfoService;

    public UseInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public List<UserInfo> list(){
        return userInfoService.list();
    }

    @GetMapping("/phone/exists")
    public R<Boolean> checkPhone(String phone){
        return R.success(userInfoService.findByPhone(phone) != null);
    }

    @PostMapping("/register")
    public R<?> register(RegisterRequest req){
        userInfoService.register(req);
        return R.success();
    }

    @PostMapping("/login")
    public R<Map<String, Object>> login(String username , String password){
        Map<String, Object>  map = userInfoService.login(username,password);
        return R.success(map);
    }

    @GetMapping("/getById")
    public R<UserInfoDTO> getById(Long id){
        return R.success(userInfoService.getDtoById(id));
    }
}
