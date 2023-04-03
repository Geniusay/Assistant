package com.genius.assistant.util;

import com.genius.assistant.pool.RegexPool;
import com.genius.assistant.util.regex.RegexUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Genius

 **/

@SpringBootTest
public class RegexUtilsTest {
    @Autowired
    RegexUtils regexUtils;
    @Test
    public void testRegexPool(){
        System.out.println(regexUtils.getRegexPool().getPhone());
    }

    @Test
    public void testRegexUtils(){
        System.out.println("邮箱是否正确："+regexUtils.isEmail("123456789@qq.com"));
        System.out.println("邮箱是否正确："+regexUtils.isEmail("123456789@qqcom"));
        System.out.println("电话是否正确："+regexUtils.isPhone("13601607121"));
        System.out.println("电话是否正确："+regexUtils.isPhone("1234567890"));
        System.out.println("电话（严格）是否正确："+regexUtils.isPhone("123456789012",true));
        System.out.println("电话（严格）是否正确："+regexUtils.isPhone("13601607121",true));
        System.out.println("QQ是否正确："+regexUtils.VerifyRegex("123456789", regexUtils.getRegexPool().getQq()));
        System.out.println("QQ是否正确："+regexUtils.VerifyRegex("1142880114", regexUtils.getRegexPool().getQq()));
    }

}
