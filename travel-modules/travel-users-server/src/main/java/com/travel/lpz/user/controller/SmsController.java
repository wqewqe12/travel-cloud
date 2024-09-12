package com.travel.lpz.user.controller;

import com.travel.lpz.core.untils.R;
import com.travel.lpz.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
/*短信接口*/
public class SmsController {
    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/register")
    public R<?> registerVerifyCode(String phone){
        // TODO 手机号格式校验
        smsService.SmsSend(phone);
        return R.success();
    }
}
