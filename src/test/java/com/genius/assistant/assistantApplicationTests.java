package com.genius.assistant;

import com.genius.assistant.common.Token;
import com.genius.assistant.helper.AutoApiJsGenerateHelper;
import com.genius.assistant.util.StringUtils;
import com.genius.assistant.util.token.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Genius
 *
 **/
@SpringBootTest
public class assistantApplicationTests {

        @Autowired
        public WebApplicationContext applicationContext;

        @Autowired
        AutoApiJsGenerateHelper autoApiJsGenerateHelper;


        @Test
        public void TestGetParam(){
                System.out.println(StringUtils.removeBrackets("String[1234]"));
                System.out.println(StringUtils.removeAngleBrackets("String<1234>"));
                System.out.println(StringUtils.removeParenthesesNotContent("String(1234)"));
        }

        @Test
        public void TestAutoApiJsGenerateHelper(){
                autoApiJsGenerateHelper.setAxiosPath("axios");
                autoApiJsGenerateHelper.setFileSavePath("E:\\Project\\Assistant\\src\\main\\resources\\js\\");
                autoApiJsGenerateHelper.generate();
        }

        @Test
        public void TestTimeUnit(){
                System.out.println(TimeUnit.valueOf("MINUTES").toSeconds(30));
        }
        class User{
                String name;
                String password;
        }
        @Autowired
        TokenUtil<User> tokenUtil;

        @Test
        public void TestTokenUtils(){
                User user = new User();
                user.name = "Genius";
                user.password = "123456";
                Token<User> user1 =
                        tokenUtil.createToken("user", user, user.name,new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(30)));
                System.out.println(user1);
                System.out.println(tokenUtil.parseTokenToToken(user1.getAccessToken(),user1.getRefreshToken(),"user",User.class));
        }
}
