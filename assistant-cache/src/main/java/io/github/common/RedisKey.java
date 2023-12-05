package io.github.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author Genius
 *   2023/08/15 23:11
 **/
@Data
public class RedisKey {
    private String key;
    private long time;
    private TimeUnit timeUnit;

    public RedisKey(String key, long time, TimeUnit timeUnit) {
        this.key = key;
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public RedisKey(String key) {
        this.key = key;
        this.time = -1;
        this.timeUnit = TimeUnit.SECONDS;
    }

    public String getKey(Object value){
        return String.format(key,value);
    }

    public String getKey(Object...values){
        return String.format(key,values);
    }
}
