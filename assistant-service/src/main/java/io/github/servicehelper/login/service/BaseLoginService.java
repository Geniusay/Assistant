package io.github.servicehelper.login.service;


import com.google.gson.JsonObject;
import io.github.entity.user.AssistantUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Genius
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
