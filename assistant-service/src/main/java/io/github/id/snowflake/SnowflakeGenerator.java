package io.github.id.snowflake;

import io.github.id.IdGenerator;
import io.github.util.random.SnowflakeUtils;
import io.github.util.time.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 分布式雪花ID生成器
 * @author Genius
 */
@Component("assistant-id-snow")
public class SnowflakeGenerator implements IdGenerator<Long> {

    /**
     * 系统开始时间戳(nano)
     */
    private static long EPOCH;

    /**
     * 系统开始时间
     * 格式: yyyy-mm-dd HH:mm:ss
     */
    @Value("${assistant.service.id.snowflake.start-time:2023-11-11 00:00:00}")
    private String startTime;

    /**
     *  机器ID所占的位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     *   数据标识id所占的位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;

    /**
     *   序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 最大机器id数
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 最大数据标识id数
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    /**
     * 最大序列在id位数
     */
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;  //机器id偏移值
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;  //最大数据标识id偏移值
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS; //时间戳偏移值

    /**
     * 时钟回拨最大容忍时间
     * 默认4ms
     */
    private static final long MAX_BACKWARD_MS = 4;

    /**
     * 雪花ID生成器的workID和dataCenterID注册机
     * register-default: 默认注册器
     * register-redis: redis注册器
     */
    @Resource(name="${assistant.service.id.snowflake.register:register-default}")
    private SnowflakeRegister snowflakeRegister;


    private long workerId;
    private long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    @PostConstruct
    public void init() throws SnowflakeRegisterException, InterruptedException {
        this.workerId = snowflakeRegister.workId();
        this.dataCenterId = snowflakeRegister.dataCenterId();
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new SnowflakeRegisterException("Worker ID must be between 0 and " + MAX_WORKER_ID);
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new SnowflakeRegisterException("Datacenter ID must be between 0 and " + MAX_DATA_CENTER_ID);
        }
        try {
            EPOCH = TimeUtil.DateTimeToLong(startTime);
        }catch (Exception e){
            throw new SnowflakeRegisterException("Snowflake start time format is not yyyy-mm-dd hh:mm:ss");
        }
    }

    private synchronized long generateId() throws SnowflakeRegisterException {

        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            long backwardTimestamp = lastTimestamp- timestamp;
            if(backwardTimestamp<MAX_BACKWARD_MS){
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(MAX_BACKWARD_MS));
                timestamp = System.currentTimeMillis();
                if (timestamp < lastTimestamp){
                    throw new SnowflakeRegisterException("Multiple clock callback issues have occurred . Refusing to generate ID for "
                            + (lastTimestamp - timestamp) +
                            " milliseconds");
                }
            }else{
                throw new SnowflakeRegisterException("Clock moved backwards,backwards time exceed "+MAX_BACKWARD_MS+"ms. Refusing to generate ID for "
                        + (lastTimestamp - timestamp) +
                        " milliseconds");
            }
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return SnowflakeUtils.snowflakeId(timestamp,EPOCH,
                workerId,dataCenterId,
                TIMESTAMP_LEFT_SHIFT,DATA_CENTER_ID_SHIFT,WORKER_ID_SHIFT,
                sequence);
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    @Override
    public Long generate() throws SnowflakeRegisterException {
        return generateId();
    }
}
