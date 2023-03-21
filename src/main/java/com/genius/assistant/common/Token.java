package com.genius.assistant.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Genius
 * @date 2023/03/21 15:27
 **/
@Data
@NoArgsConstructor
public class Token<T> implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private T payload;

    private String accessToken;

    private String refreshToken;

    private String iss;         //token签发者

    private String sub;         //token所面向的用户

    private String aud;         //接收token的一方

    private Date accessExp;         //access-token的过期时间，这个过期时间必须要大于签发时间

    private Date refreshExp;         //refresh-token的过期时间，这个过期时间必须要大于签发时间

    private Date nbf;         //定义在什么时间之前，该jwt都是不可用的.

    private Date iat;         //token的签发时间

    private String jti;         //jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。

    public Token(String accessToken,String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token(String accessToken, String refreshToken, String iss, String sub, String aud, Date accessExp, Date refreshExp, Date nbf, Date iat, String jti) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.iss = iss;
        this.sub = sub;
        this.aud = aud;
        this.accessExp = accessExp;
        this.refreshExp = refreshExp;
        this.nbf = nbf;
        this.iat = iat;
        this.jti = jti;
    }


}
