package com.genius.assistant.util.file;

import com.genius.assistant.util.path.PathUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Genius
 * @date 2023/03/22 22:13
 **/
public class CommonFileUtils {

    public static void writeFile(String file,String fileContent) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file))) {
            bufferedOutputStream.write(fileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

}
