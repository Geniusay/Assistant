package com.genius.assistant.util;

import com.genius.assistant.util.token.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Genius
 * @date 2023/03/24 22:14
 **/
@SpringBootTest
public class TokenUtilsTest {
    @Autowired
    TokenUtil tokenUtils;

    @Test
    public void testToken(){
        String token = tokenUtils.createToken("Genius",new Object(),"123456").getAccessToken();
        System.out.println(tokenUtils.parseTokenToToken(token, "Genius", Object.class));
    }

}
