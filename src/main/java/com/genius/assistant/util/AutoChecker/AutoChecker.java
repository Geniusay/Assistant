package com.genius.assistant.util.AutoChecker;

import com.genius.assistant.util.regex.RegexUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Genius
 * @date 2023/04/13 02:05
 **/
@Component
public class AutoChecker {

    public static <T> boolean check(Map<T,Checker<? super T>> params){
        for (Map.Entry<T, Checker<? super T>> entry : params.entrySet()) {
            if (!entry.getValue().goCheck(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkByRegex(Map<String,String> params){
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!RegexUtils.VerifyRegex(entry.getKey(),entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
