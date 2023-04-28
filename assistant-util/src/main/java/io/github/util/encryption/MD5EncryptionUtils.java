package io.github.util.encryption;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Genius

 **/

/*Md5加密工具类*/
@Component
public class MD5EncryptionUtils implements EncryptionUtil {

    private final static char HexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public String Encipher(String password) {
        byte[] bytes = password.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = HexChars[b >>> 4 & 0xf];
                chars[k++] = HexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);

        }
    }

    @Override
    public String Decrypt(String password) {
        return password;
    }
}

