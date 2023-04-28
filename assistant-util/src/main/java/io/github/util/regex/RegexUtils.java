package io.github.util.regex;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Genius

 **/

/*
* 正则表达式工具类
 */
@Data
@Component
@AllArgsConstructor
public class RegexUtils {

    @Autowired
    RegexPool regexPool;
    //是否是邮箱
    public boolean isEmail(String email){
        return VerifyRegex(email,regexPool.getEmail());
    }

    /*
     是否是电话
     @param strict 是否严格
     */
    public boolean isPhone(String phone,boolean strict){
        return VerifyRegex(phone,strict?regexPool.getStrictPhone():regexPool.getPhone());
    }

    //是否是电话
    public boolean isPhone(String phone){
        return isPhone(phone,false);
    }

    //是否是用户名
    public boolean isUserName(String username){
        return VerifyRegex(username,regexPool.getUsername());
    }

    //是否是密码
    public boolean isPassword(String password){
        return VerifyRegex(password,regexPool.getPassword());
    }

    //URL是否正确
    public boolean isURL(String url){
        return VerifyRegex(url,regexPool.getUrl());
    }

    //是否带有端口号的url
    public boolean isPortUrl(String portUrl){
        return VerifyRegex(portUrl,regexPool.getUrlPort());
    }

    public static boolean VerifyRegex(String str,String regex){
        //正则表达式检测
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }


}
