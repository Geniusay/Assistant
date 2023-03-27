package com.genius.assistant.warmup.generate;

import com.genius.assistant.base.MethodDecompose;
import com.genius.assistant.common.apiJs.ApiJs;
import com.genius.assistant.util.StringUtils;
import com.genius.assistant.util.VelocityUtils;
import com.genius.assistant.util.file.CommonFileUtils;
import com.genius.assistant.util.path.PathUtils;
import com.genius.assistant.warmup.generate.autotemplate.ApiJsTemplateFile;
import com.genius.assistant.warmup.generate.autotemplate.TemplateFile;
import com.genius.assistant.warmup.generate.builder.ApiJsTemplateBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Genius
 *
 **/

/**
 * ApiJs自动生成器
 * 可以根据controller生成对应的apiJs文件，目前支持以下功能
 * 1，异步生成
 * 2，传统Js生成
 * 3，自定义模板生成
 */
@Component
@Data
@AllArgsConstructor
public class AutoApiJsGenerate extends AutoGenerate {

    @Autowired
    ApiJsTemplateBuilder apiJsTemplateBuilder;

    private ApiJsTemplateFile apiJsTemplateFile;
    private Map<String,Boolean> excludingControllerMap;


    private boolean isJsMoodleGenerated = false;    //是否使用js模块化生成方式

    public AutoApiJsGenerate(){
        this.excludingControllerMap = new HashMap<>();
        setTemplateName("apiJs.java.vm");
        setTemplatePath("src/main/resources/templates/");
        setFileSavePath("src/main/resources/static/js/");
    }
    /*整个自动生成器的所有先前配置操作*/
    //过滤掉不需要生成js文件的controller
    public AutoApiJsGenerate filter(List<Class> excludingControllers){
        for (Class excludingController : excludingControllers) {
            this.excludingControllerMap.put(excludingController.getSimpleName(), true);
        }
        return this;
    }
    //设置Axios路径
    public AutoApiJsGenerate setAxiosPath(String axiosPath){
        if(Objects.isNull(apiJsTemplateFile)){
            apiJsTemplateBuilder.setAxiosPath(axiosPath);
        }else {
            this.apiJsTemplateFile.setFileAxiosPath(axiosPath);
        }
        return this;
    }

    public AutoApiJsGenerate setIsJsMoodleGenerated(boolean isJsMoodleGenerated){
        this.isJsMoodleGenerated = isJsMoodleGenerated;
        return this;
    }

    //构建js模板信息文件
    @Override
    public TemplateFile buildTemplateFile() {
        if(this.apiJsTemplateFile == null){
            this.apiJsTemplateFile = apiJsTemplateBuilder.build();
        }
        return this.apiJsTemplateFile;
    }

    //选择生成文档方式
    private void generateChoose(){
        if(isJsMoodleGenerated){
            generateJsMoodle();
        } else if(!StringUtils.isEmpty(this.getTemplatePath())){
            generateByTemplate();
        } else{
            getLogger().error("无可用生成方法");
        }
    }

    //异步方式构建和生成js文件
    @Override
    public void generate(Boolean isAsync){
        buildTemplateFile();
        if(isAsync){
            //TODO 需要优化线程开辟
            new Thread(this::generateChoose).start();
        }else{
            generateChoose();
        }
    }

    @Override
    public void generate(){
        if(StringUtils.isEmpty(this.getFileSavePath())){
            throw new RuntimeException("文件保存路径不能为空");
        }
        generate(false);
    }

    //传统方式构建和生成js文件
    private void generateJsMoodle(){
        String fileSuffix = apiJsTemplateFile.getFileSuffix();
        String fileHead = "import request from '"+apiJsTemplateFile.getFileAxiosPath()+"';\r"; //构造js文件头，导入axios
        for (ApiJs apiJs : apiJsTemplateFile.getApiJsList()) {
            StringBuilder mouldBuilder = new StringBuilder();
            if(excludingControllerMap.containsKey(apiJs.getControllerClassName())){
                continue;
            }

            String fileName = apiJs.getControllerClassName()+fileSuffix;
            mouldBuilder.append(fileHead);
            apiJs.getApiMethodList().forEach(apiMethod -> {
                String methodName = apiMethod.getMethodName();
                String url = apiMethod.getMethodUrl()+"/'";
                List<String> params = apiMethod.getMethodParam();
                String type = apiMethod.getMethodType();
                Boolean isRestFul = apiMethod.getIsRestFul();
                String JsFunction = methodName + "(";
                StringBuilder JsParamsStr = new StringBuilder();

                JsParamsStr.append(type.equals("post")?"data: {\r":"params: {\r");
                for(int i = 0; i < params.size(); i++){
                    String param = params.get(i);
                    JsFunction += param;
                    JsParamsStr.append("     ").append(param).append(": ").append(param).append(",\r");
                    if(i != params.size() - 1){
                        JsFunction += ",";
                    }
                    if(isRestFul){
                        url += "+"+param+"+'/'";
                    }
                }
                JsFunction += ")";
                JsParamsStr.append("   }\r");

                mouldBuilder.append(String.format("\rexport function %s {\r" +
                                " return request({\r" +
                                "   url: '%s,\r" +
                                "   method: '%s',\r" +
                                "   %s\r",JsFunction,url,type,
                                isRestFul?"":JsParamsStr.toString()))
                        .append(" });\r").append("}\r");
            });
            generateFile(fileName, mouldBuilder);
        }
    }

    //根据模板文件生成js文件，不建议直接调用，而是用generate去调用
    @Override
    public void generateByTemplate() {

        for (ApiJs apiJs : apiJsTemplateFile.getApiJsList()) {
            if(excludingControllerMap.containsKey(apiJs.getControllerClassName())){
                continue;
            }
            VelocityContext apiJsInfo = VelocityUtils.getVelocityContextByObject("apiJsInfo",apiJs);
            apiJsInfo.put("axiosPath",apiJsTemplateFile.getFileAxiosPath());
            try {
                generateFile(apiJsInfo,apiJs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    //生成文件 --- 传统方式
    private void generateFile(String fileName, StringBuilder mouldBuilder) {
        String fileSavePath = getFileSavePath();
        assert (!StringUtils.isEmpty(fileSavePath));
        String file = PathUtils.smartFilePath(fileSavePath,fileName);
        getLogger().info("-----生成文件-----:{}",file);

        CommonFileUtils.writeFile(file,mouldBuilder.toString());
    }

    //生成文件 --- 模板方式
    private void generateFile(VelocityContext apiJsInfo,ApiJs apiJs) throws Exception {

        String fileName = apiJs.getControllerClassName()+ apiJsTemplateFile.getFileSuffix();

        VelocityUtils.generateFileByVelocity(apiJsInfo,
                getTemplateName(),getTemplatePath(),
                PathUtils.smartFilePath(getFileSavePath(), fileName));
    }


}
