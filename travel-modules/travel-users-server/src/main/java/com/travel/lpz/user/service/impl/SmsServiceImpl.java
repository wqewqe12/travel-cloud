package com.travel.lpz.user.service.impl;
import cn.hutool.core.util.RandomUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.tea.TeaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.lpz.core.exception.BusinessException;
import com.travel.lpz.core.untils.R;
import com.travel.lpz.redis.utils.RedisCache;
import com.travel.lpz.user.redis.key.UserRedisKeyPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.travel.lpz.user.service.SmsService;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
//    @Value("${aliyun.dysms.templateCode}")
//    private String templateCode;
//    @Value("${aliyun.dysms.sign}")
//    private String sign;
    @Autowired
    private RedisCache redisCache;
    private final Client smsClient;

    public SmsServiceImpl(Client smsClient) {
        this.smsClient = smsClient;
    }

    @Override
    public void SmsSend(String phone) {
        // 1.验证手机号合法。用jrs303
        // 略
        //2.生成验证码
        String code = RandomUtil.randomNumbers(6);
        //3.验证码保存到redis
        UserRedisKeyPrefix keyPrefix = UserRedisKeyPrefix.USER_REGISTER_VERIFY_CODE_STRING;
        redisCache.setCacheObject(keyPrefix.fullKey(phone) ,code, keyPrefix.getTimeout(), keyPrefix.getUnit());
        //3.发送验证码
        try{
//            this.send(phone,code);
            log.info("短信:{}",code);
            System.out.println(code);
        }catch (RuntimeException re){
            throw  re;
        }catch (Exception e){
            e.printStackTrace();
        }
        //.......
    }
    /*
    * 阿里云短信服务
    */
//    private void send(String phone ,String code)throws Exception{
//        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest =
//                new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
//                        .setTemplateParam("{\"code\":\"" + code +"\"}")
//                .setPhoneNumbers(phone)
//                .setSignName(sign)
//                .setTemplateCode(templateCode);
//        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
//        try {
//            // 复制代码运行请自行打印 API 的返回值
//            SendSmsResponse response = smsClient.sendSmsWithOptions(sendSmsRequest, runtime);
//            ObjectMapper mapper = new ObjectMapper();
//            SendSmsResponseBody body = response.getBody();
//            String respJson = mapper.writeValueAsString(body);
//            log.info("短信服务:{}",respJson);
//            if (!"ok".equalsIgnoreCase(body.code)){
//                throw new BusinessException(R.CODE_SMS_ERROR,body.message);
//            }
//        } catch (TeaException error) {
//            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
//            // 错误 message
//            System.out.println(error.getMessage());
//            // 诊断地址
//            System.out.println(error.getData().get("Recommend"));
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        } catch (Exception _error) {
//            TeaException error = new TeaException(_error.getMessage(), _error);
//            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
//            // 错误 message
//            System.out.println(error.getMessage());
//            // 诊断地址
//            System.out.println(error.getData().get("Recommend"));
//            com.aliyun.teautil.Common.assertAsString(error.message);
//        }
//    }
}
