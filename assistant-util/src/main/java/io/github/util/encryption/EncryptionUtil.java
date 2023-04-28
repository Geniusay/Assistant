package io.github.util.encryption;

public interface EncryptionUtil {
    String Encipher(String password) throws Exception;
    String Decrypt(String password) throws Exception;
}
