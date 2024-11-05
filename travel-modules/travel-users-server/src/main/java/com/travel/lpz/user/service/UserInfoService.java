package com.travel.lpz.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.lpz.user.domain.UserInfo;
import com.travel.lpz.user.dto.UserInfoDTO;
import com.travel.lpz.user.vo.RegisterRequest;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    /*
    * 基于手机号查询用户对象*/

    UserInfo findByPhone(String phone);

    void register(RegisterRequest req);

    /**
     * @param username
     * @param password
     * @return {token ,用户}
     */
    Map<String, Object> login(String username, String password);

    UserInfoDTO getDtoById(Long id);
}
