package com.genius.assistant.util;

import com.genius.assistant.common.Token;
import com.genius.assistant.util.token.TokenUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Genius

 **/
@SpringBootTest
public class TokenUtilsTest {
    @Autowired
    TokenUtil<User> tokenUtils;
    class User{
        private String name;
        private int age;
        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }
    @Test
    public void testCreateToken(){
        User user = new User("Genius",18);
        Claims claims = Jwts.claims();
        claims.put("user",user);

        String token = tokenUtils.createToken("user",user,"123456").getAccessToken();
        String token2 = tokenUtils.createToken(Map.of("user",user),"123456").getAccessToken();
        String token3 = tokenUtils.createToken(
                Map.of("type","jwt","alg","HS256"), //token头信息
                claims,                                             //token payload
                new Date(),                                         //token签发时间
                new Date(new Date().getTime() + 60*1000),           //token过期时间
                new Date(new Date().getTime() + 120*1000),          //token刷新时间,如果未启用则无效
                new Date(new Date().getTime()),           //Token生效时间
                "Genius",                                           //token签名者
                "public Path",                                      //公共区域
                tokenUtils.getJwtTokenProperties().getJit(),        //token唯一身份标识
                "Genius",                                           //发布人
                true                                               //是否需要进行刷新操作
                ).getAccessToken();
        System.out.println(tokenUtils.parseTokenToToken(token, "user", User.class));
        System.out.println(tokenUtils.parseTokenToToken(token2, "user", User.class));
        System.out.println(tokenUtils.parseTokenToToken(token3, "user", User.class));
        System.out.println(tokenUtils.parseTokenToObj(token3, "user", User.class));
    }

    @Test
    public void tesReFreshToken(){
        User user = new User("Genius",18);
        Claims claims = Jwts.claims();
        claims.put("user",user);
        Token<User> token = tokenUtils.createToken(
                Map.of("type","jwt","alg","HS256"), //token头信息
                claims,                                             //token payload
                new Date(),                                         //token签发时间
                new Date(new Date().getTime() + 10),           //token过期时间
                new Date(new Date().getTime() + 120*1000),          //token刷新时间,如果未启用则无效
                new Date(new Date().getTime()),           //Token生效时间
                "Genius",                                           //token签名者
                "public Path",                                      //公共区域
                tokenUtils.getJwtTokenProperties().getJit(),        //token唯一身份标识
                "Genius",                                           //发布人
                true                                               //是否需要进行刷新操作
        );
        System.out.println(token.getAccessToken());
        System.out.println(tokenUtils.refreshToken(token).getAccessToken());

    }

}
