package com.travel.lpz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.core.exception.BusinessException;
import com.travel.lpz.core.untils.Md5Untils;
import com.travel.lpz.core.untils.R;
import com.travel.lpz.redis.utils.RedisCache;
import com.travel.lpz.user.mapper.UserInfoMapper;
import com.travel.lpz.user.redis.key.UserRedisKeyPrefix;
import com.travel.lpz.user.service.UserInfoService;
import com.travel.lpz.user.domain.UserInfo;
import com.travel.lpz.user.vo.RegisterRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private RedisCache redisCache;
    @Override
    public UserInfo findByPhone(String phone) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<UserInfo>()
                .eq("phone", phone);
        return getOne(wrapper);
    }

    @Override
    public void register(RegisterRequest req) {
        UserInfo userInfo =this.findByPhone(req.getPhone());
        //基于手机号查询是否已经又该手机号
        if (userInfo != null) {
            throw new BusinessException(R.CODE_REGISTER_ERROR ,"该手机号已经注册,请勿重复注册");
        }
        //redis中获取验证码，与前端校验
        //USERS:REGISTER:VERIFY_CODE:手机号
        String fullKey = UserRedisKeyPrefix.USER_REGISTER_VERIFY_CODE_STRING.fullKey(req.getPhone());
        String code = redisCache.getCacheObject(fullKey);
        if (!req.getVerifyCode().equals(code)) {
            throw new BusinessException(R.CODE_REGISTER_ERROR,"验证码错误");
        }
        //验证码删除
        redisCache.deleteObjcet(fullKey);
        //创建用户，填入参数
        userInfo = this.buidlerUserInfo(req);
        //对密码加密
        //加盐+加散列次数
        String encryptPassword = Md5Untils.getMD5(userInfo.getPassword() + userInfo.getPhone());
        userInfo.setPassword(encryptPassword);
        System.out.println(encryptPassword);
        //保存用户到数据库
        super.save(userInfo);
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        //1.基于用户名查询用户对象
        UserInfo userInfo = this.findByPhone(username);
        if (userInfo == null) {
            throw new BusinessException(500401,"该用户不存在");
        }
        //2.对前段传入密码加密
        String encryptPassword = Md5Untils.getMD5(password+username);
        //3.校验密码
        if (!encryptPassword.equalsIgnoreCase(userInfo.getPassword())) {
            throw new BusinessException(500402,"用户名或密码错误");
        }
        //4.if(通过)，使用jwt生成token，并往jwt中存入用户信息
        Map<String, Object> payload = new HashMap<>();
        payload.put("id",userInfo.getId());
        payload.put("nickname",userInfo.getNickname());
        payload.put("loginTime", System.currentTimeMillis());
        payload.put("expireTime",30);
        String jwtToken = Jwts.builder()
                .addClaims(payload)
                .signWith(SignatureAlgorithm.HS256, "travel")
                .compact();

        //5.构建map，存入token和用户信息，返回
        payload.clear();
        payload.put("token",jwtToken);
        //返回的user属性需要过滤密码，使用@JSONIGNORE注解
        payload.put("user",userInfo);
        return payload;
    }

    private UserInfo buidlerUserInfo(RegisterRequest req) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(req,userInfo);
//        userInfo.setPhone(req.getPhone());
//        userInfo.setNickname(req.getNickname());
//        userInfo.setPassword(req.getPassword());
        userInfo.setInfo("人机一个 ");
        userInfo.setHeadImgUrl("/images/default.jpg");
        userInfo.setState(UserInfo.STATE_NORMAL);

        return userInfo;
    }
}
