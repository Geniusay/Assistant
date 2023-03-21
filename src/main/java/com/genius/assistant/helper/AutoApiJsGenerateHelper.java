package com.genius.assistant.helper;

import com.genius.assistant.base.MethodDecompose;
import com.genius.assistant.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Genius
 *
 **/

/*
* 生成前端api的js文件
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoApiJsGenerateHelper {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private MethodDecompose methodDecompose;    //方法分解器

    private WebApplicationContext webApplicationContext;

    private String axiosPath; //前端axios的导入路径

    private String fileSavePath;    //文件保存路径

    private final static String prefix = ".js"; //文件后缀

    private final static String GET = "get";
    private final static String POST = "post";


    private Map<Class,Boolean> excludingControllerMap;
    @Autowired
    public AutoApiJsGenerateHelper(MethodDecompose methodDecompose, WebApplicationContext webApplicationContext) {
        this.excludingControllerMap = new HashMap<>();
        this.methodDecompose = methodDecompose;
        this.webApplicationContext = webApplicationContext;
    }

    //过滤掉不需要生成js文件的controller
    public AutoApiJsGenerateHelper filter(List<Class> excludingControllers){
        for (Class excludingController : excludingControllers) {
            this.excludingControllerMap.put(excludingController, true);
        }
        return this;
    }

    //函数核心
    public void generate(Boolean isAsync){
        if(isAsync){
            //TODO 需要优化线程开辟
            new Thread(this::generateJsMoodle).start();
        }else{
            generateJsMoodle();
        }
    }

    public void generate(){
        generate(false);
    }

    //构建和生成js文件
    private void generateJsMoodle(){
        //生成文件
        //生成文件内容
        //保存文件
        String fileName = "";
        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        StringBuilder mouldBuilder = new StringBuilder();

        String fileHead = "import request from '"+axiosPath+"';\r"; //构造js文件头，导入axios
        mouldBuilder.append(fileHead);
        for (RequestMappingInfo info : methodMap.keySet()){
            //获取controller的名字
            String type = StringUtils.removeBracketsNotContent(info.getMethodsCondition().getMethods().toString()).toLowerCase(); //返回post和get请求方式
            Class controllerClass = methodMap.get(info).getBeanType();

            if(StringUtils.isEmpty(type)){ //type必须为post或get
                continue;
            }
            if(excludingControllerMap.containsKey(controllerClass)){ //过滤掉不需要生成js文件的controller
                continue;
            }

            String[] split = controllerClass.getName().split("\\.");
            assert (split.length>0);
            String controllerName = split[split.length-1];
            assert (!StringUtils.isEmpty(controllerName));

            if(fileName.equals("")){
                fileName = controllerName;
            }else if(!fileName.equals(controllerName)) {
                //文件名不一致，说明已经遍历完一个controller，开始生成文件
                generateFile(fileName, mouldBuilder);
                mouldBuilder = new StringBuilder();
                fileName = controllerName;
                mouldBuilder.append(fileHead);
            }
            Method method = methodMap.get(info).getMethod();
            String methodName = method.getName();

            List<String> methodParamName = methodDecompose.getMethodParamNameByDiscover(method); //获取参数名

            String url = StringUtils.removeBracketsNotContent(info.getPatternsCondition().getPatterns().toString()); //返回url
            assert (!StringUtils.isEmpty(url));

            String JsFunctionStr = methodName+"("+StringUtils.removeBracketsNotContent(methodParamName.toString())+")"; //构成js函数形式

            //构建url
            String JsUrl = url;
            if(type.equals(GET) && url.contains("{")){
                JsUrl = url.replaceAll("\\{", "'+").replaceAll("}", "").trim();
            }
            //构建参数,参数不为空才构建
            StringBuilder JsParamsStr = new StringBuilder();
            if (JsUrl.equals(url)) {
                JsUrl += "'";
                if(!methodParamName.isEmpty()){
                    if(type.equals(GET)){
                        JsParamsStr.append("param: {\r");

                    }else if(type.equals(POST)){
                        JsParamsStr.append("data: {\r");
                    }
                    methodParamName.forEach((param)->{
                        JsParamsStr.append("     ").append(param).append(": ").append(param).append(",\r");
                    });
                    //如果不是RestFul类型请求，且url中没有参数，需要在url后面加上单引号
                    JsParamsStr.append("   }\r");
                }
            }
            //构建函数体
            mouldBuilder.append(String.format("\rexport function %s {\r" +
                    " return request({\r" +
                    "   url: '%s,\r" +
                    "   method: '%s',\r" +
                    "   %s\r",JsFunctionStr,JsUrl,type,JsParamsStr.toString()))
                    .append(" });\r").append("}\r");
        }
        generateFile(fileName, mouldBuilder);
    }

    //生成文件
    private void generateFile(String fileName, StringBuilder mouldBuilder) {
        String file = fileSavePath + fileName +prefix;
        logger.info("-----生成文件-----:{}",file);
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file))) {
            bufferedOutputStream.write(mouldBuilder.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
