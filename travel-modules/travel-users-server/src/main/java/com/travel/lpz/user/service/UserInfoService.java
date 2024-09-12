package com.travel.lpz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.lpz.user.user.domain.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    /*
    * 基于手机号查询用户对象*/

    UserInfo findByPhone(String phone);
}
