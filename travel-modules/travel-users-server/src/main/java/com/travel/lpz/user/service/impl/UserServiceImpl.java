package com.travel.lpz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.user.mapper.UserInfoMapper;
import com.travel.lpz.user.service.UserInfoService;
import com.travel.lpz.user.user.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Override
    public UserInfo findByPhone(String phone) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>()
                .eq("phone", phone);
        return getOne(wrapper);
    }
}
