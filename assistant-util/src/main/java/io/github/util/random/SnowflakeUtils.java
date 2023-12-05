package io.github.util.random;

import io.github.util.time.TimeUtil;

import java.time.LocalDateTime;

/**
 *  雪花算法随机数生成器
 */
public class SnowflakeUtils {


    /**
     * 生成一个雪花ID
     * @param epoch      //起始时间戳
     * @return long 雪花ID
     */
    public static long snowflakeId(long epoch){
        long timestamp = System.currentTimeMillis();
        return ((timestamp - epoch) << 42);
    }

    /**
     * 生成一个雪花ID
     * @param timestamp  //当前时间戳
     * @param epoch      //起始时间戳
     * @param timestamp_left_shift //时间戳左移位数
     * @param sequence             //序列号
     * @return long 雪花ID
     */
    public static long snowflakeId(long timestamp,long epoch,long timestamp_left_shift,long sequence){
        return ((timestamp - epoch) << timestamp_left_shift)
                | sequence;
    }


    /**
     * 生成一个雪花ID(包含数据中心ID和机器ID)
     * @param timestamp  //当前时间戳
     * @param epoch      //起始时间戳
     * @param workerId   //机器ID
     * @param dataCenterId //数据中心ID
     * @param timestamp_left_shift //时间戳左移位数
     * @param data_center_id_shift //数据中心ID左移位数
     * @param worker_id_shift      //机器ID左移位数
     * @param sequence             //序列号
     * @return long 雪花ID
     */
    public static long snowflakeId(long timestamp,long epoch,long workerId,long dataCenterId
            ,long timestamp_left_shift,long data_center_id_shift,long worker_id_shift,long sequence){
        return ((timestamp - epoch) << timestamp_left_shift)
                | (dataCenterId << data_center_id_shift)
                | (workerId << worker_id_shift)
                | sequence;
    }

}
