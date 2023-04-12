package com.genius.assistant.servicehelper.login.service;

import com.genius.assistant.common.NodeResult;
import com.genius.assistant.common.Result;
import com.genius.assistant.common.Status;
import com.genius.assistant.common.user.AssistantUser;
import com.genius.assistant.servicehelper.login.ReentrantCheck;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Genius
 * @date 2023/04/12 02:36
 **/
@Component
@AllArgsConstructor
public class FormLoginService implements LoginService{


    @Override
    public NodeResult execute() {
        return null;
    }

    @Override
    public Result login(Object obj) {
        return null;
    }
}
