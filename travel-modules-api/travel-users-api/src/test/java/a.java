import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static com.travel.lpz.core.untils.Md5Untils.bytesToHex;

/**
 * @author bxc
 * @title a
 * @date 2024/9/22 20:06
 * @description TODO
 */
public class a {

    public static String getMD5(){
        String message = "12345619836397122";
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
}
