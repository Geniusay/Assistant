package io.github.exception.handler;

import io.github.common.logger.CommonLogger;
import lombok.Data;
import org.springframework.core.Ordered;

import javax.annotation.Resource;

@Data
public abstract class AbstractExceptionHandler implements Ordered {

    @Resource
    protected CommonLogger commonLogger;
}
