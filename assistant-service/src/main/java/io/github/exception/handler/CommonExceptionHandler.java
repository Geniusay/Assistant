package io.github.exception.handler;

import io.github.common.logger.CommonLogger;
import io.github.common.web.Result;
import io.github.exception.handler.annotation.AssistantControllerAdvice;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AssistantControllerAdvice("commonExceptionHandler")
@Conditional(AssistantExceptionHandlerCondition.class)
public class CommonExceptionHandler extends AbstractExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> ExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        commonLogger.error("系统异常:异常类:%s , 异常信息:%s",exception.getClass().getSimpleName(),exception.getMessage());
        return Result.error("500","未知异常");
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
