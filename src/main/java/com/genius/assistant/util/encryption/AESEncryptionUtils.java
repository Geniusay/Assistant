package com.genius.assistant.util.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Genius

 **/

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class AESEncryptionUtils implements EncryptionUtil{

    public static final String ALGORITHM = "AES";

    private SecretKey secretKey;

    /**
     * 生成密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator secretGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        secretGenerator.init(secureRandom);
        SecretKey secretKey = secretGenerator.generateKey();
        return secretKey;
    }

    private Charset charset = Charset.forName("UTF-8");


    private byte[] aes(byte[] contentArray, int mode, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, secretKey);
        byte[] result = cipher.doFinal(contentArray);
        return result;
    }

    @Override
    public String Encipher(String password) throws Exception {
        if(secretKey == null)
            throw new RuntimeException("SecretKey is null, please generate a SecretKey first");
        return new String(aes(password.getBytes(charset), Cipher.ENCRYPT_MODE, secretKey),charset);
    }

    @Override
    public String Decrypt(String password) throws Exception {
        if(secretKey == null)
            throw new RuntimeException("SecretKey is null, please generate a SecretKey first");
        byte[] result = aes(password.getBytes(), Cipher.DECRYPT_MODE, secretKey);
        return new String(result, charset);
    }
}
