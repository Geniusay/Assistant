package com.genius.assistant.servicehelper.login;

import com.genius.assistant.common.Result;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Genius
 * @date 2023/04/13 01:31
 **/
@Component
public interface ReentrantCheck<T> {
    Result<T> reentrantCheck(Map<String,Object> map);
}
