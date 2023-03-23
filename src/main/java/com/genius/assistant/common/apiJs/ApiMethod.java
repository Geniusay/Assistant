package com.genius.assistant.common.apiJs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Genius
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiMethod {
    private String methodName;   //方法名
    private String methodUrl;    //方法url
    private String methodType;   //方法类型
    private String methodDesc;   //方法描述
    private List<String> methodParam = new ArrayList<>();  //方法参数
    private Boolean isRestFul;   //是否是restful风格
}
