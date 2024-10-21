package com.travel.lpz.article.untils;


import cn.hutool.core.date.DateTime;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.UrlSafeBase64;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;
import com.qiniu.storage.Configuration;

import java.io.*;
import java.util.UUID;
import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qiniu.util.*;
import okhttp3.*;


@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "qiniuyun.upload")
@Data
public class QiNiuUntils {

        @Value("${qiniuyun.upload.AccessKey}")
        private  String accessKey;

        @Value("${qiniuyun.upload.SecretKey}")
        private  String secretKey;

        @Value("${qiniuyun.upload.BucketName}")
        private  String bucket;
        @Value("${qiniuyun.upload.Mkdir}")
        //上传目录
        private  String mkdir;
        @Value("${qiniuyun.upload.url}")
        private  String domain;

        public String upload(MultipartFile file) throws IOException {
                if (file.isEmpty()) {
                        throw new RuntimeException("文件是空的");
                }
                // 创建上传token
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                // 设置上传配置，Region要与存储空间所属的存储区域保持一致
                Configuration cfg = new Configuration(Region.region2());
                // 创建上传管理器
                UploadManager uploadManager = new UploadManager(cfg);
                String originalFilename = file.getOriginalFilename();
                // 构造文件目录和文件名
                assert originalFilename != null;
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileKey = mkdir + UUID.randomUUID() + suffix;
                // 上传文件
                Response response = uploadManager.put(file.getInputStream(), fileKey, upToken, null, null);
                // 返回文件url
                if (!response.isOK()) {
                        // 打印返回的信息
                        System.out.println(response.bodyString());
                        throw new RuntimeException("文件上传失败");
                }
                return domain +"/"+ fileKey;
        }

        public String getUpToken() {
                Auth auth = Auth.create(accessKey, secretKey);
                return auth.uploadToken(bucket, null, 3600, new StringMap().put("insertOnly", 1));
        }
        public String uploadBy64(MultipartFile file) throws Exception {
                if (file.isEmpty()) {
                        throw new RuntimeException("文件是空的");
                }
                //这里将原本的图片转换为base64字符串
                byte[] src = file.getBytes();
                String file64 = Base64.encodeToString(src, 0);
                Integer length = src.length;
                String dateStr = DateTime.now().toString();
                // 将当前日期拼接在文件名称中
                String fileName = dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + ".jpg";
                String url = domain + length + "/key/"+ UrlSafeBase64.encodeToString(fileName);
                //非华东空间需要根据注意事项 : 修改上传域名
                RequestBody requestBody = RequestBody.create(null, file64);
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/octet-stream")
                        .addHeader("Authorization", "UpToken " + getUpToken())
                        .post(requestBody).build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                // 上传成功返回图片地址
                return response.isSuccessful()?("http://" + bucket + "/" + fileName):"Up Img Failed";
        }



//        public static String uploadPicByBase64(byte[] base64, String fileName) throws Exception{
//                String file64 = Base64.encodeToString(base64, 0);
//                Integer length = base64.length;
//                String dateStr = LocalDate.now().toString();
//                // 将当前日期拼接在文件名称中
//                fileName = dateStr + "/" + fileName;
//                String url = "http://upload.qiniu.com/putb64/" + length + "/key/"+ UrlSafeBase64.encodeToString(fileName);
//                //非华东空间需要根据注意事项 : 修改上传域名
//                RequestBody requestBody = RequestBody.create(null, file64);
//                Request request = new Request.Builder()
//                        .url(url)
//                        .addHeader("Content-Type", "application/octet-stream")
//                        .addHeader("Authorization", "UpToken " + getUpToken())
//                        .post(requestBody).build();
//                OkHttpClient client = new OkHttpClient();
//                okhttp3.Response response = client.newCall(request).execute();
//                // 上传成功返回图片地址
//                return response.isSuccessful()?("http://" + bucketNameUrl + "/" + fileName):"Up Img Failed";
//        }


//        public String uploadImageQiniu(MultipartFile multipartFile){
//                try {
//                        //1、获取文件上传的流
//                        byte[] fileBytes = multipartFile.getBytes();
//                        //2、创建日期目录分隔
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                        String datePath = dateFormat.format(new Date());
//
//                        //3、获取文件名
//                        String originalFilename = multipartFile.getOriginalFilename();
//                        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//                        String filename = datePath+"/"+UUID.randomUUID().toString().replace("-", "")+ suffix;
//
//                        //4.构造一个带指定 Region 对象的配置类
//                        //Region根据自己的对象空间的地址选
//                        Configuration cfg = new Configuration(Region.region2());
//                        UploadManager uploadManager = new UploadManager(cfg);
//
//                        //5.获取七牛云提供的 token
//                        Auth auth = Auth.create(accessKey, secretKey);
//                        String upToken = auth.uploadToken(BucketName);
//                        uploadManager.put(fileBytes, filename, upToken);
//
//                        return url+"/"+filename;
//                } catch (IOException e) {
//                        e.printStackTrace();
//                }
//                return null;
//        }

}