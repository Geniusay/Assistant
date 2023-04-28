package io.github.util.autochecker;


import io.github.util.regex.RegexPool;
import io.github.util.regex.RegexUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 自动检查器
 */
@Component
public class AutoChecker {

    //检查参数是否符合要求,传入类型和Checker接口
    public static <T> boolean check(Map<T,Checker<? super T>> params){
        for (Map.Entry<T, Checker<? super T>> entry : params.entrySet()) {
            if (!entry.getValue().goCheck(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    //检查参数是否符合要求,传入类型和正则表达式
    public static boolean checkByRegex(Map<String,String> params){
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!RegexUtils.VerifyRegex(entry.getKey(),entry.getValue())) {
                return false;
            }
        }
        return true;
    }

}
