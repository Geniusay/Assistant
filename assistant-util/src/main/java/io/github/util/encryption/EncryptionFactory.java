package io.github.util.encryption;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *    Genius

 **/
/*
* 加密工具工厂
 */
public class EncryptionFactory {

    //需要优化
    private static ConcurrentHashMap<String,EncryptionUtil> encryptionUtilMap = new ConcurrentHashMap<>(
            Map.of(
                    EncryptionType.MD5.toString(),new MD5EncryptionUtils(),
                    EncryptionType.RSA.toString(),new RSAEncryptionUtils(),
                    EncryptionType.AES.toString(),new AESEncryptionUtils(),
                    EncryptionType.DES.toString(),new DESEncryptionUtils(),
                    EncryptionType.Base64.toString(),new Base64EncryptionUtils()
            )
    );

    public static EncryptionUtil getEncryptionUtil(EncryptionType type){
        return getEncryptionUtil(type.toString());
    }

    public static EncryptionUtil getEncryptionUtil(String type){
        if(!encryptionUtilMap.containsKey(type))
            throw new RuntimeException("EncryptionUtil not found");
        return encryptionUtilMap.get(type);
    }

    //添加加密工具
    public static void add(String type,EncryptionUtil encryptionUtil){
        encryptionUtilMap.put(type,encryptionUtil);
    }

}
