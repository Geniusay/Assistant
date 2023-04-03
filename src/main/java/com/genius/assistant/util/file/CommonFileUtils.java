package com.genius.assistant.util.file;

import com.genius.assistant.util.path.PathUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

/**
 * @author Genius
 **/
public class CommonFileUtils {

    /**
     * 读取文件
     * @param file
     */
    public static void writeFile(String file,String fileContent) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file))) {
            bufferedOutputStream.write(fileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
    public static File createFile(String fileSavePath,String fileName){
        return createFile(PathUtils.smartFilePath(fileSavePath,fileName));
    }

    /**
     * MultipartFile 转 File
     * @param multipartFile
     * @return
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
