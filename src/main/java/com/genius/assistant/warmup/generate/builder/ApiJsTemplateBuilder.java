package com.genius.assistant.warmup.generate.builder;

import com.genius.assistant.base.MethodDecompose;
import com.genius.assistant.common.apiJs.ApiJs;
import com.genius.assistant.common.apiJs.ApiMethod;
import com.genius.assistant.util.StringUtils;
import com.genius.assistant.warmup.generate.autotemplate.ApiJsTemplateFile;
import com.genius.assistant.warmup.generate.autotemplate.TemplateFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @date 2023/03/23 18:20
 **/

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ApiJsTemplateBuilder implements TemplateBuilder<ApiJsTemplateFile> {

    @Autowired
    private MethodDecompose methodDecompose;    //方法分解器

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String axiosPath; //前端axios的导入路径

    private final static String GET = "get";

    private final static String POST = "post";

    @Override
    public ApiJsTemplateFile build() {
        preBuild();
        Map<String, ApiJs> apiJsMap = new HashMap<>();

        ApiJsTemplateFile apiJsTemplateFile = new ApiJsTemplateFile();
        apiJsTemplateFile.setFileAxiosPath(axiosPath);

        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        for(RequestMappingInfo info: methodMap.keySet()){
            String type = StringUtils.removeBracketsNotContent(info.getMethodsCondition().getMethods().toString()).toLowerCase();
            if(!type.equals(GET) && !type.equals(POST)){
                continue;
            }
            Class controllerClass = methodMap.get(info).getBeanType();
            String className = controllerClass.getSimpleName();
            ApiJs apiJs;
            if(apiJsMap.containsKey(className)) {
                apiJs = apiJsMap.get(className);
            }else{
                apiJs = new ApiJs();
                apiJs.setControllerClassName(className);
                apiJsMap.put(className, apiJs);
            }
            //构造apiMethod
            ApiMethod apiMethod = new ApiMethod();
            Method method = methodMap.get(info).getMethod();
            String methodName = method.getName();
            apiMethod.setIsRestFul(false);
            List<String> methodParam = methodDecompose.getMethodParamNameByDiscover(method);
            apiMethod.setMethodName(methodName);
            apiMethod.setMethodParam(methodParam);

            String url = StringUtils.removeBracketsNotContent(info.getPatternsCondition().getPatterns().toString()); //返回url
            assert (!StringUtils.isEmpty(url));

            String JsUrl = url;
            if(type.equals(GET) && url.contains("{")){
                JsUrl = StringUtils.removeBraces(url);
                JsUrl = JsUrl.substring(0, JsUrl.length()-1);
                apiMethod.setIsRestFul(true);
            }
            apiMethod.setMethodUrl(JsUrl);
            apiMethod.setMethodType(type);

            apiJs.getApiMethodList().add(apiMethod);
            apiJsTemplateFile.getApiJsList().add(apiJs);
        }
        return apiJsTemplateFile;
    }

    public void preBuild() {
        if(StringUtils.isEmpty(axiosPath)){
            axiosPath = "axios";
        }
    }
}
