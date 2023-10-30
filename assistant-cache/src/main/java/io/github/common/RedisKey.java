package io.github.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author Genius
 * @date 2023/08/15 23:11
 **/
@Data
@AllArgsConstructor
public class RedisKey {
    private String key;
    private long time;
    private TimeUnit timeUnit;

    public String getKey(Object value){
        return String.format(key,value);
    }

    public String getKey(Object...values){
        return String.format(key,values);
    }
}
