package io.github.exception.handler;

import io.github.common.web.Result;
import io.github.exception.handler.annotation.AssistantControllerAdvice;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * spring web异常处理类
 */
@AssistantControllerAdvice("springWebExceptionHandler")
@Conditional(AssistantExceptionHandlerCondition.class)
public class SpringWebExceptionHandler extends AbstractExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    protected Result<?> MissingServletRequestParameterExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","缺少参数");
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    protected Result<?> MissingPathVariableExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","缺少路径变量");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    protected Result<?> HttpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","不支持的 HTTP 方法");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    protected Result<?> HttpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","不支持的媒体类型");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    protected Result<?> HttpMediaTypeNotAcceptableExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","不可接受的媒体类型");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    protected Result<?> HttpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","请求体异常");
    }

    @ExceptionHandler( HttpMessageNotWritableException.class)
    @ResponseBody
    protected Result<?> HttpMessageNotWritableExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","响应体异常");
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    protected Result<?> ServletRequestBindingExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","请求绑定异常");
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    @ResponseBody
    protected Result<?> ConversionNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","参数异常");
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    protected Result<?> TypeMismatchExceptionHandler(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        return Result.error("400","类型不匹配");
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE-2;
    }
}
