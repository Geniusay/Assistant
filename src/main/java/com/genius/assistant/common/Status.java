package com.genius.assistant.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Genius

 **/

/**
 * 状态码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    private String code;    //状态码

    private String msg;     //状态信息

    private Exception exception;//异常

    //将HttpStatus转换为Status
    public static Status HttpStatusToStatus(HttpStatus httpStatus){
        return new Status(String.valueOf(httpStatus.value()),httpStatus.getReasonPhrase(),null);
    }

}
