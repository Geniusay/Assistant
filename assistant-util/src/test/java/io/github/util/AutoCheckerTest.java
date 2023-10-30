package io.github.util;


import io.github.MainApplication;
import io.github.util.autochecker.AutoChecker;
import io.github.util.regex.RegexPattern;
import io.github.util.regex.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

/**
 *    Genius
 **/
@SpringBootTest(classes = MainApplication.class)
public class AutoCheckerTest {

    @Test
    public void testRegexChecker(){
        boolean b = AutoChecker.checkByRegex(Map.of(
                "123456789@qq.com", RegexPattern.EMAIL,
                "13601607121", RegexPattern.PHONE,
                "1142880114", RegexPattern.QQ)
        );
        System.out.println(b);

        boolean a = AutoChecker.checkByRegex(Map.of(
                "123456789@qq.com", RegexPattern.EMAIL,
                "13601607121", RegexPattern.PHONE,
                "1142880114123", RegexPattern.QQ)
        );
        System.out.println(a);
    }

    @Test
    public void testChecker(){
        boolean b = AutoChecker.check(Map.of(
                1234,(obj)->(Integer)obj>1000,
                "12345", (obj)->((String)obj).length() >3,
                "Geniusay",(obj)-> RegexUtils.VerifyRegex((String)obj,RegexPattern.QQ),
                "123456789@qq.com",(obj)->RegexUtils.VerifyRegex((String)obj,RegexPattern.EMAIL),
                new Date(),(obj)->((Date)obj).getTime()>100
                )
        );
        System.out.println(b);
    }
}
