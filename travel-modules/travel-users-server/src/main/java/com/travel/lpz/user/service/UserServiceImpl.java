package com.travel.lpz.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.user.service.mapper.UserInfoMapper;
import com.travel.lpz.UserInfoService;
import com.travel.lpz.user.domain.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
