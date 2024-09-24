package com.travel.lpz.core.untils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class Md5Untils {
    public static String getMD5(String message){
        String md5 = "";
        try {
            //创建md5对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte  = message.getBytes(StandardCharsets.UTF_8);
            //获取md5字节数组
            byte[] md5Byte = md.digest(messageByte);
            md5 = bytesToHex(md5Byte);
        }catch (Exception e){
            e.printStackTrace();
        }
        return md5;
    }
/*
* 二进制转十六进制
*/
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0){
                num+=25;
            }
            if (num <16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }

    /*
    * 签名：请求参数排序并后面补充key值，最后md5加密
    */
    public static String signatures(Map<String , Object> params){
        String signatures = "";
        try {
            List<String> paramsStr = new ArrayList<>();
            for (String key1 : params.keySet()){
                if (null != key1 && !"".equals(key1)){
                    paramsStr.add(key1);
                }
            }
            Collections.sort(paramsStr);
            StringBuffer sbff = new StringBuffer();
            for (String kk : paramsStr){
                String value = params.get(kk).toString();
                if ("".equals(sbff.toString())){
                    sbff.append(kk + "=" + value);
                }else{
                    sbff.append("&" + kk + "=" + value);
                }
            }
            //加上key值
            signatures = getMD5(sbff.toString()).toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        return signatures;
    }



}
