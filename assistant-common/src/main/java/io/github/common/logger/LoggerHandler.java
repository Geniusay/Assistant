package io.github.common.logger;

public interface LoggerHandler {

    void info(String msg, Object... args);

    void error(String msg, Object... args);

    void warn(String msg, Object... args);

    void debug(String msg, Object... args);
}
