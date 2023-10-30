package io.github.util.encryption;


import io.github.util.file.CommonFileUtils;
import io.github.util.path.PathUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *    Genius

 **/

/*
    * Base64加密
 */
@Component
public class Base64EncryptionUtils implements EncryptionUtil {

    @Override
    public String Encipher(String password) {
        try {
            return Base64Utils.encodeToString(password.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    @Override
    public String Decrypt(String cipher) {
        try {
            byte[] bytes = Base64Utils.decodeFromString(cipher);
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
        }
       return cipher;
    }

    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param imgPath
     * @return
     */
    public String getImageStr(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        String encode = null;
        // 对字节数组Base64编码
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            encode = Base64.encodeBase64String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * 将Base64编码的字符串转换成图片
     *
     * @param imgData     图片编码
     * @param fileName    图片名称
     * @return
     */
    public String generateImage(String imgData, String fileName)  {
        if (imgData == null) {
            // 图像数据为空
            return "null";
        }
        ApplicationHome applicationHome = new ApplicationHome(FileUtils.class);
        File source = applicationHome.getSource();
        String dirPath = source.getParentFile().toString() + "/upload";

        File dir = CommonFileUtils.createFile(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file = CommonFileUtils.createFile(PathUtils.smartFilePath(dirPath, fileName));
        if (file.exists()){
            file.delete();
        }
        try {
            // Base64解码
            byte[] b = Base64.decodeBase64(imgData);
            for (int i = 0; i < b.length; ++i) {
                // 调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(PathUtils.smartFilePath(dirPath, fileName));
            out.write(b);
            out.flush();
            out.close();
            return PathUtils.smartFilePath(dirPath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }
}
