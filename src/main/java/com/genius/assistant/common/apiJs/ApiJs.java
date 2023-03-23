package com.genius.assistant.common.apiJs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Genius
 **/

/**
 * apiJs的文件信息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiJs {
    private String controllerClassName;  //控制器类名
    private List<ApiMethod> apiMethodList = new ArrayList<>();  //api方法列表


}
