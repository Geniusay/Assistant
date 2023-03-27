package com.genius.assistant.common;

import com.genius.assistant.pool.StatusPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Genius
 * @date 2023/03/27 21:23
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

    private static <T> void success(Status status, Result result){
        result.setCode(status.getCode());
        result.setMsg(status.getMsg());
    }

    public static Result success(){
        Result result = new Result();
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

    public static Result error(String code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    public static Result error(Status error){
        Result result = new Result();
        result.setCode(error.getCode());
        result.setMsg(error.getMsg());
        return result;
    }

    public static Result throwError(Boolean Condition, Status error) throws Exception {
        if (Condition) {
            throw error.getException();
        }
        return Result.success();
    }

    public static Result throwError(Status error) throws Exception {
        return throwError(true,error);
    }


}
