package com.genius.assistant.servicehelper.login.service;

import com.genius.assistant.common.Result;
import com.genius.assistant.common.user.AssistantUser;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Genius
 * @date 2023/04/12 03:02
 **/
@Component
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseLoginService<T> implements LoginService<T>{
    private boolean checkReentrantLogin = false;
    private boolean autoRegister = false;
    private AssistantUser user;
    private Map paramChecker;
    private Object authGenerator;

}
