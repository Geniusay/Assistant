package com.genius.assistant.util.path;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Genius
 **/

/*
    * 路径工具类
    * 目前可以适配linux的文件路径拼接
 */
@Component
public class PathUtils {
    //获取当前项目的路径
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    //组装文件路径，适配Linux和Windows，无需在意paths的格式错误，自动为了修正格式
    // TODO需要优化算法
    public static String smartFilePath(String... paths) {
        return smartFilePath(File.separatorChar, paths);
    }

    public static String smartFilePath(char type,String...paths){
        StringBuilder sb = new StringBuilder();
        String separator = String.valueOf(type);
        String replace = separator.equals("\\") ? "/" : "\\";
        for (String path : paths) {
            path = path.replace(replace, separator);
            if (!path.contains(".")&&!path.endsWith(separator)) {
                path = path + separator;
            }
            if (path.startsWith(separator)) {
                path = path.substring(1);
            }
            sb.append(path);
        }
        String res = sb.toString();
        //将多个分隔符替换为一个分隔符
        if(separator.equals("\\"))
            res = res.replaceAll("\\\\+", "\\\\");
        else
            res = res.replaceAll("\\/+", "\\/");

        return res.startsWith(separator) ? res : separator + res;
    }

}
