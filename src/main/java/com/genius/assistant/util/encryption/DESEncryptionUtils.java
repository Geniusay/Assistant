package com.genius.assistant.util.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Genius
 * @date 2023/03/24 01:14
 **/
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class DESEncryptionUtils implements EncryptionUtil {


    public String key = "12345678";


    @Override
    public String Encipher(String password) throws Exception {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return new String(cipher.doFinal(password.getBytes()));
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String Decrypt(String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return new String(cipher.doFinal(password.getBytes()));
    }
}
