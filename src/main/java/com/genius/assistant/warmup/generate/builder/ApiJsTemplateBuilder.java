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
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Genius
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

    /*
    只会生成带有RequestMapping注解的方法，且必须标注方法类型Get或者Post，否则不会生成
     */
    @Override
    public ApiJsTemplateFile build() {
        preBuild();
        Map<String, ApiJs> apiJsMap = new HashMap<>();

        ApiJsTemplateFile apiJsTemplateFile = new ApiJsTemplateFile();
        apiJsTemplateFile.setFileAxiosPath(axiosPath);

        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        for(RequestMappingInfo info: methodMap.keySet()){
            Object[] types = info.getMethodsCondition().getMethods().toArray();

            if(types.length == 0){
                continue;
            }

            for (Object obj : types) {
                String type = obj.toString().toLowerCase();
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
                extractedMethod(methodMap, info, types, type, apiMethod);

                apiJs.getApiMethodList().add(apiMethod);

            }

        }
        apiJsMap.values().forEach(ApiJs->{
            apiJsTemplateFile.getApiJsList().add(ApiJs);
        });
        return apiJsTemplateFile;
    }

    //构造apiMethod
    private void extractedMethod(Map<RequestMappingInfo, HandlerMethod> methodMap, RequestMappingInfo info, Object[] types, String type, ApiMethod apiMethod) {
        Method method = methodMap.get(info).getMethod();
        String methodName = types.length>1? multiMethod(method.getName(), type):method.getName();

        apiMethod.setIsRestFul(false);
        List<String> methodParam = methodDecompose.getMethodParamNameByDiscover(method);
        apiMethod.setMethodName(methodName);
        apiMethod.setMethodParam(methodParam);

        String url = versionControl_getPatternsCondition(info); //采用版本控制获取url
        assert (!StringUtils.isEmpty(url));

        String JsUrl = url;
        if(type.equals(GET) && url.contains("{")){
            JsUrl = StringUtils.removeBraces(url);
            JsUrl = JsUrl.substring(0, JsUrl.length()-1);
            apiMethod.setIsRestFul(true);
        }
        apiMethod.setMethodUrl(JsUrl);
        apiMethod.setMethodType(type);
    }

    public void preBuild() {
        if(StringUtils.isEmpty(axiosPath)){
            axiosPath = "axios";
        }
    }

    private String multiMethod(String methodName,String type){
        return methodName + type.toUpperCase();
    }

    /*
    以下为版本控制代码，由于高版本的springboot改用getPathPatternsCondition方法，
    而本框架采用的是低版本springboot的getPatternCondition方法，导致项目报错异常，
    所以采用版本控制方法，来获取url
     */
    private String versionControl_getPatternsCondition(RequestMappingInfo info) {
        PatternsRequestCondition patternsCondition = info.getPatternsCondition();
        String url = "";
        if(patternsCondition != null){
            url =  info.getPatternsCondition().getPatterns().toString();
        }else{
            //说明为高版本
            try {
                PathPatternsRequestCondition pathPatternsCondition = (PathPatternsRequestCondition)RequestMappingInfo.
                        class.getMethod("getPathPatternsCondition").invoke(info);
                if(pathPatternsCondition != null){
                    url = pathPatternsCondition.getPatterns().toString();
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return StringUtils.removeBracketsNotContent(url);
    }
}
