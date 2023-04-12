package com.genius.assistant.servicehelper.login.service;

import com.genius.assistant.common.Result;
import com.genius.assistant.servicehelper.ServiceNode;
import com.google.gson.JsonObject;

import java.util.Map;

public interface LoginService<Map> extends ServiceNode<Map> {

    Result<Map> login(Map obj);
}
