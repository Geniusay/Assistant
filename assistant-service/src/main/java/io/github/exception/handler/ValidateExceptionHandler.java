package io.github.exception.handler;

import io.github.common.logger.CommonLogger;
import io.github.common.web.Result;
import io.github.exception.handler.annotation.AssistantControllerAdvice;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

@AssistantControllerAdvice("validateExceptionHandler")
@Conditional(AssistantExceptionHandlerCondition.class)
public class ValidateExceptionHandler extends AbstractExceptionHandler{

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Result<?> ValidationExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){

        if(exception instanceof ValidationException){
            commonLogger.error("请求路径:%s 参数校验错误%s",request.getRequestURI(),exception.getMessage());
            return Result.error("400",String.format("参数校验错误:%s",exception.getMessage()));
        }
        return Result.error("400","参数校验错误");
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE-3;
    }
}
