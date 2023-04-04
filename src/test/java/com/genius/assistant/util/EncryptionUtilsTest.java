package com.genius.assistant.util;

import com.genius.assistant.util.encryption.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

/**
 * @author Genius

 **/

@SpringBootTest
public class EncryptionUtilsTest {


    @Test
    public void testMD5Encryption() throws Exception {
        String md5Encipher = EncryptionFactory.getEncryptionUtil(EncryptionType.MD5).Encipher("123456");
        System.out.println("md5加密："+ md5Encipher);
        System.out.println("md5解密："+ EncryptionFactory.getEncryptionUtil(EncryptionType.MD5).Decrypt(md5Encipher));

        String rsaEncipher = EncryptionFactory.getEncryptionUtil(EncryptionType.RSA).Encipher("123456");
        System.out.println("rsa加密："+ rsaEncipher);
        System.out.println("rsa解密："+ EncryptionFactory.getEncryptionUtil(EncryptionType.RSA).Decrypt(rsaEncipher));

        AESEncryptionUtils encryptionUtil = (AESEncryptionUtils) EncryptionFactory.getEncryptionUtil(EncryptionType.AES);
        SecretKey secretKey = encryptionUtil.generateKey();
        encryptionUtil.setSecretKey(secretKey);
        String encipher = encryptionUtil.Encipher("123456");
        System.out.println("aes加密："+ encipher);
        System.out.println("aes解密："+ encryptionUtil.Decrypt(encipher));

        String desEncipher = EncryptionFactory.getEncryptionUtil(EncryptionType.DES).Encipher("123456");
        System.out.println("des加密："+ desEncipher);
        System.out.println("des解密："+ EncryptionFactory.getEncryptionUtil(EncryptionType.DES).Decrypt(desEncipher));
    }

    @Test
    public void newVersionEncryption() throws Exception{
        EncryptionFactory.add("MD55",new MD5EncryptionUtils());
        String md55 = EncryptionFactory.getEncryptionUtil("MD55").Encipher("123456");
        System.out.println("md55加密："+ md55);
        System.out.println("md55解密："+ EncryptionFactory.getEncryptionUtil("MD55").Decrypt(md55));
    }
}
