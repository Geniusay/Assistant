package io.github.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Genius
 * @date 2023/10/26 22:00
 **/
@Component("sl4j")
public class Sl4jLoggerHandler implements LoggerHandler{
    private static final String LOGGER_NAME = "review_service";

    private final static Logger REVIEW_LOGGER = LoggerFactory.getLogger(LOGGER_NAME);

    @Override
    public void info(String msg, Object... args) {
        REVIEW_LOGGER.info(String.format(msg, args));
    }

    @Override
    public void error(String msg, Object... args) {
        REVIEW_LOGGER.error(String.format(msg, args));
    }

    @Override
    public void warn(String msg, Object... args) {
        REVIEW_LOGGER.warn(String.format(msg, args));
    }

    @Override
    public void debug(String msg, Object... args) {
        REVIEW_LOGGER.debug(String.format(msg, args));
    }
}
