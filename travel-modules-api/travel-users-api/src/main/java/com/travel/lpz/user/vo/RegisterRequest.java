package com.travel.lpz.user.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
/*
* 用户注册请求传递的参数*/
@Setter
@Getter
public class RegisterRequest {
    private String phone;
    private String nickname;
    private String password;
    private String verifyCode;
}
