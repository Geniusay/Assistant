package io.github.servicehelper.login.service;


import com.google.gson.JsonObject;
import io.github.common.Result;
import io.github.servicehelper.ServiceNode;

import java.util.Map;

public interface LoginService<Map> extends ServiceNode<Map> {

    Result<Map> login(Map obj);
}
