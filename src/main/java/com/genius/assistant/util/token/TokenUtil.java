package com.genius.assistant.util.token;

import com.genius.assistant.common.Token;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


/**
 * @author Genius
 *
 **/
@Data
@Component
public class TokenUtil<T> {

    @Resource
    private JWTTokenProperties jwtTokenProperties;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //生成密匙
    public SecretKey generalKey() {
        byte[] encodeKey = Base64.decodeBase64(jwtTokenProperties.getTokenSecret());
        SecretKey key = Keys.hmacShaKeyFor(encodeKey);
        return key;
    }

    /*创建Token*/

    private Claims creatClaims(String name,T payload) {
        Claims claims = Jwts.claims();
        claims.put(name, new Gson().toJson(payload));
        return claims;
    }

    private Claims createClaims(Map<String,Object> payload) {
        Claims claims = Jwts.claims();
        claims.putAll(payload);
        return claims;
    }

    /*
        * 创建token
        * @param header 头部
        * @param claims 私有部分,不建议存放私密信息
        * @param iat jwt签发时间
        * @param exp jwt过期时间
        * @param nbf jwt生效时间
        * @param iss jwt签名者
        * @param sub 公共部分
        * @param jti jwt唯一身份标识，jti
        * @param audience 发布人
        * @param isRefresh 是否刷新token
     */
    public Token<T> createToken(Map<String,Object> header, Claims claims
            , Date iat, Date accessExp,Date refreshExp, Date nbf
            , String iss, String sub, String jti,String audience,boolean isRefresh){

        JwtBuilder jwtBuilder = Jwts.builder();

        if(!Objects.isNull(header)){
            jwtBuilder.setHeader(header);
        }

        jwtBuilder.setClaims(claims)    //私有部分,不建议存放私密信息
                .setId(jti)         //jwt唯一身份标识，jti
                .setIssuedAt(iat)  //jwt签发时间
                .setExpiration(accessExp)//jwt过期时间
                .setNotBefore(nbf) //jwt生效时间
                .setIssuer(iss)    //jwt签名者
                .setSubject(sub)   //公共部分
                .setAudience(audience)     //发布人
                .signWith(generalKey());     //设置签名


        String accessToken = jwtBuilder.compact();
        String refreshToken = "";
        if(jwtTokenProperties.getEnableRefresh()&&isRefresh){
            refreshToken = jwtBuilder.setExpiration(refreshExp).compact();
        }else{
            refreshExp = null;
        }

        return new Token<>(accessToken, refreshToken, iss, sub, audience, accessExp, refreshExp, nbf, iat, jti);
    }

    public Token<T> createToken(String payloadName,T payload,String subject) {
        Claims claims = creatClaims(payloadName, payload);
        return createToken(null,claims,new Date(),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getAccessTokenExpireTime()),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getRefreshTokenExpireTime()),
                null,null,subject, jwtTokenProperties.getJit(), null,true);
    }

    public Token<T> createToken(String payloadName,T payload,String subject,Date nbf) {
        Claims claims = creatClaims(payloadName, payload);
        return createToken(null,claims,new Date(),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getAccessTokenExpireTime()),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getRefreshTokenExpireTime()),
                nbf,null,subject, jwtTokenProperties.getJit(), null,true);
    }

    public Token<T> createToken(Map<String,Object> payload,String subject) {
        Claims claims = createClaims(payload);
        return createToken(null,claims,new Date(),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getAccessTokenExpireTime()),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getRefreshTokenExpireTime()),
                null,null,subject, jwtTokenProperties.getJit(), null,true);
    }

    public Token<T> createToken(Token<T> token,String name){
        return createToken(null,creatClaims(name,token.getPayload()),token.getIat(),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getAccessTokenExpireTime()),
                new Date(System.currentTimeMillis() + jwtTokenProperties.getRefreshTokenExpireTime()),
                token.getNbf(),token.getIss(),token.getSub(), jwtTokenProperties.getJit(), null,true);
    }

    /*解析Token*/

    //解析Token
    public Claims parseToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //TODO 这里应该抛出TOKEN异常给全局异常捕捉
            return null;
        }
        return claims;
    }

    //解析Token字符串变为对象
    public T parseTokenToObj(String token, String payloadName, Class<T> clazz) {
        Claims claims = parseToken(token);
        String json = claims.get(payloadName).toString();
        return new Gson().fromJson(json, clazz);
    }

    //解析Token字符串变为Token对象
    public Token<T> parseTokenToToken(String accessToken,String refreshToken,String payloadName,Class<T> clazz){
        Claims claims = parseToken(accessToken);
        String json = (String) claims.get(payloadName);

        Token<T> tokenObj = new Token<T>(accessToken,refreshToken,claims.getIssuer(),claims.getSubject(),claims.getAudience(),
                claims.getExpiration(),claims.getExpiration(),claims.getNotBefore(),
                claims.getIssuedAt(),claims.getId());

        tokenObj.setPayload(new Gson().fromJson(json, clazz));
        return tokenObj;
    }

    public Token<T> parseTokenToToken(String accessToken,String name,Class<T> clazz){
        return parseTokenToToken(accessToken,"",name,clazz);
    }


    /*验证Token，Token有效返回true，Token无效返回false
    * TODO 这里的状态码应该可以供开发者选择
    *  */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*更新Token
    * 当access-token过期时，会使用refresh-token来更新access-token
    * 如果refresh-token也过期了则返回null
    * */
    public Token<T> refreshToken(String oldToken,String refreshToken,Map<String,Object> header){
        if(jwtTokenProperties.getEnableRefresh()){
            logger.info("refresh token is disable,if you want to enable it,please set JWTTokenProperties.EnableRefreshToken to true");
            if (validateToken(oldToken)) {
                if(!validateToken(refreshToken)){
                    Jws<Claims> claimsJws = Jwts.parser()
                            .setSigningKey(generalKey())
                            .parseClaimsJws(refreshToken);

                    Claims claims = claimsJws.getBody();

                    Token<T> token = createToken(header, claims, claims.getIssuedAt(),
                            new Date(System.currentTimeMillis() + jwtTokenProperties.getAccessTokenExpireTime()),
                            new Date(System.currentTimeMillis() + jwtTokenProperties.getRefreshTokenExpireTime()),
                            claims.getNotBefore(), claims.getIssuer(), claims.getSubject(),
                            jwtTokenProperties.getJit(), claims.getAudience(), false);
                    token.setRefreshToken(refreshToken);
                    return token;
                }else{
                    return null;
                }
            }
        }

        return new Token<T>(oldToken,refreshToken);
    }

    public Token<T> refreshToken(String oldToken,String refreshToken){
        return refreshToken(oldToken,refreshToken,null);
    }

}
