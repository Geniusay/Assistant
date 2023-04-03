package com.genius.assistant.util.encryption;

/**
 * @author Genius

 **/

public class EncryptionFactory {

    public static EncryptionUtil getEncryptionUtil(EncryptionType type){
        switch (type){
            case MD5:
                return new MD5EncryptionUtils();
            case Base64:
                return new Base64EncryptionUtils();
            case AES:
                return new AESEncryptionUtils();
            case DES:
                return new DESEncryptionUtils();
            case RSA:
                return new RSAEncryptionUtils();
            default:
                throw new RuntimeException("Encryption type not found");
        }
    }

}
