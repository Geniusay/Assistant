package io.github.id.snowflake;


import io.github.common.RedisKey;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * TODO Redis锁优化成 Redisson
 * Redis雪花算法注册机
 * 配置项assistant.service.id.snowflake.register: register-redis时启用
 */
@Component("register-redis")
@ConditionalOnProperty(name = "assistant.service.id.snowflake.register", havingValue = "register-redis")
public class RedisSnowflakeRegister implements SnowflakeRegister{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final static RedisKey WORK_ID_KEY = new RedisKey("assistant:snowflake:workId");
    private final static RedisKey DATACENTER_ID_KEY = new RedisKey("assistant:snowflake:datacenterId");
    private final static RedisKey LOCK = new RedisKey("assistant:snowflake:lock",1, TimeUnit.MINUTES);

    private static final int MAX_WORKER_ID = 32;
    private static final int MAX_DATACENTER_ID = 32;

    private void tryLock() throws InterruptedException {
        if (Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(LOCK.getKey(), "1", LOCK.getTime(), LOCK.getTimeUnit()))) {
            return;
        }
        Thread.sleep(10);
        tryLock();
    }

    private void unlock(){
        stringRedisTemplate.delete(LOCK.getKey());
    }

    @PostConstruct
    public void init(){
        stringRedisTemplate.opsForValue().setIfAbsent(WORK_ID_KEY.getKey(),"0");
        stringRedisTemplate.opsForValue().setIfAbsent(DATACENTER_ID_KEY.getKey(),"0");
    }

    @Override
    public long workId() throws SnowflakeRegisterException, InterruptedException {
        long workId;
        try {
            tryLock();
            Long increment = Optional.ofNullable(stringRedisTemplate.opsForValue().increment(WORK_ID_KEY.getKey())).orElse(0L);
            if(increment==0){
                throw new SnowflakeRegisterException("redis generate workId error,please check redis config");
            }
            workId = increment-1;
            if(workId==MAX_WORKER_ID){
                stringRedisTemplate.opsForValue().increment(DATACENTER_ID_KEY.getKey());
                stringRedisTemplate.opsForValue().set(WORK_ID_KEY.getKey(),"0");
                workId=0;
                return workId;
            }
        }finally {
            unlock();
        }
        return workId;
    }

    @Override
    public long dataCenterId() throws SnowflakeRegisterException {
        String incrementStr = stringRedisTemplate.opsForValue().get(DATACENTER_ID_KEY.getKey());
        if(incrementStr==null){
            throw new SnowflakeRegisterException("redis generate dataCenterId error,please check redis config");
        }
        long increment = Long.parseLong(incrementStr);
        return increment>=MAX_DATACENTER_ID?0:increment;
    }
}
