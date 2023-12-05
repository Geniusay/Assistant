package io.github.id.snowflake;

public interface SnowflakeRegister {

    long workId() throws SnowflakeRegisterException, InterruptedException;

    long dataCenterId() throws SnowflakeRegisterException;
}
