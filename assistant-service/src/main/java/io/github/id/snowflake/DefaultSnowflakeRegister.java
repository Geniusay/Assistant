package io.github.id.snowflake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 默认雪花算法注册机
 * 用户手动配置assistant.service.id.snowflake.workerId和assistant.service.id.snowflake.dataCenterId设置workId与dataCenterId
 *  默认workId与dataId都为0
 */
@Component("register-default")
public class DefaultSnowflakeRegister implements SnowflakeRegister{

    @Value("${assistant.service.id.snowflake.workerId:0}")
    private long workerId;

    @Value("${assistant.service.id.snowflake.dataCenterId:0}")
    private long dataCenterId;

    @Override
    public long workId() {
        return workerId;
    }

    @Override
    public long dataCenterId() {
        return dataCenterId;
    }
}
