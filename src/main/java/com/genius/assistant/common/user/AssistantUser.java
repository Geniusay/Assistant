package com.genius.assistant.common.user;

import com.mysql.cj.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;

/**
 * @author Genius
 * @date 2023/04/12 03:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssistantUser {

    private String username;  // 用于存储用户的用户名
    private String uid;
    private String password;  // 用于存储用户的密码
    private String confirmPassword;  // 用于存储用户的二级密码

    private String phone;  // 用于存储用户的手机号
    private String email;  // 用于存储用户的邮箱
    private String phoneCode;// 用于存储用户的手机验证码
    private String emailCode;// 用于存储用户的邮箱验证码

    private Cookie cookie; // 用于存储用户的cookie
    private String token;  // 用于存储用户的token
    private Session session; // 用于存储用户的session
}
