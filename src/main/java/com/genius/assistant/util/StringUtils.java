package com.genius.assistant.util;

/**
 * @author Genius
 * @date 2023/03/20 23:58
 **/

/*
    * 字符串工具类
 */
public class StringUtils extends org.springframework.util.StringUtils {


/* 去除操作 */
    //去除小括号包括其中的内容
    public static String removeParentheses(String str){
        return str.replaceAll("\\(.*\\)","").trim();
    }

    //去除大括号包括其中的内容
    public static String removeBraces(String str){
        return str.replaceAll("\\{.*\\}","").trim();
    }

    //去除中括号包括其中的内容
    public static String removeBrackets(String str){
        return str.replaceAll("\\[.*\\]","").trim();
    }

    //去除双引号
    public static String removeDoubleQuotes(String str){
        return str.replaceAll("\"","").trim();
    }

    //去除单引号
    public static String removeSingleQuotes(String str){
        return str.replaceAll("'","").trim();
    }

    //去除尖括号包括其中的内容
    public static String removeAngleBrackets(String str){
        return str.replaceAll("<.*>","").trim();
    }

    //去除小括号不包括其中的内容
    public static String removeParenthesesNotContent(String str){
        return str.replaceAll("\\(|\\)", "").trim();
    }

    //去除大括号不包括其中的内容
    public static String removeBracesNotContent(String str){
        return str.replaceAll("\\{|\\}", "").trim();
    }

    //去除中括号不包括其中的内容
    public static String removeBracketsNotContent(String str){
        return str.replaceAll("\\[|\\]", "").trim();
    }

    //去除空格
    public static String removeSpace(String str){
        return str.replaceAll(" ","").trim();
    }

}
