package io.github.servicehelper.login;


import io.github.common.Result;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Genius
 **/
@Component
public interface ReentrantCheck<T> {
    Result<T> reentrantCheck(Map<String,Object> map);
}
