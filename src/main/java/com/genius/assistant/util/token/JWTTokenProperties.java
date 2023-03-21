package com.genius.assistant.util.token;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Genius
 * @date 2023/03/21 15:16
 **/
@Data
@ConfigurationProperties(prefix = "assistant.token")
public class JWTTokenProperties {

    private String accessTokenName="accessToken"; //访问token的header名称
    private long accessTokenExpireTime = 30;       //访问token过期时间
    private String accessTokenTimeUnit = "MINUTES";  //token过期时间单位

    private String refreshTokenName = "refreshToken"; //刷新token的header名称

    private Boolean enableRefresh = false; //是否开启刷新token
    private long refreshTokenExpireTime = 24;       //刷新token过期时间
    private String refreshTokenTimeUnit = "HOURS"; //token过期时间单位

    private String tokenSecret = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ="; //token密钥

    private Boolean isRandomJIT = false; //是否随机生成jti

    private static final String JIT = UUID.randomUUID().toString();
    //根据accessTokenExpireTimeUnit返回不同的时间
    public long getAccessTokenExpireTime() {
        //根据tokenExpireTimeUnit返回不同的时间
       return TimeUnit.valueOf(accessTokenTimeUnit).toMillis(accessTokenExpireTime);
    }

    public long getRefreshTokenExpireTime(){
        if(!enableRefresh) return 0;

        return TimeUnit.valueOf(refreshTokenTimeUnit).toMillis(refreshTokenExpireTime);
    }

    public String getJit(){
        if(isRandomJIT) return UUID.randomUUID().toString();

        return JIT;
    }
}
