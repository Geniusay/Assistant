package com.genius.assistant.common;

import com.genius.assistant.pool.StatusPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author Genius

 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private String code;

    private String msg;

    private T data;

    private String timestamp;

    public Result(T data) {
        this.data = data;
    }

    private static <T> void success(Status status, Result<T> result){

        result.setCode(status.getCode());
        result.setMsg(status.getMsg());
    }

    public static <T> Result<T> success(){
        Result<T> result = new Result<T>();
        success(StatusPool.Success,result);
        return result;
    }

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>(data);
        success(StatusPool.Success,result);
        return result;
    }

    public static <T> Result<T> success(T data,Status status){
        Result<T> result = new Result<>(data);
        success(status,result);
        return result;
    }

    public static <T> Result<T> error(String code,String msg){
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    public static <T> Result<T> error(Status error){
        Result<T> result = new Result<T>();
        result.setCode(error.getCode());
        result.setMsg(error.getMsg());
        return result;
    }

    //抛出Status异常
    public static <T> Result<T> throwError(Boolean Condition, Status error) throws Exception {
        if(error!=null&&error.getException()!=null){
            throw new RuntimeException("Status异常为Null");
        }
        if (Condition) {
            throw error.getException();
        }
        return Result.success();
    }

    public static <T> Result<T> throwError(Status error) throws Exception {
        return throwError(true,error);
    }


}
