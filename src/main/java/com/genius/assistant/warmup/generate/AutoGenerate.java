package com.genius.assistant.warmup.generate;

import com.genius.assistant.util.path.PathUtils;
import com.genius.assistant.warmup.generate.autotemplate.TemplateFile;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Genius
 **/
@Data
@Component
public abstract class AutoGenerate {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String fileSavePath = PathUtils.getProjectPath();    //文件保存路径

    private String templatePath = "src/main/resources/templates/";    //模板路径

    private String templateName;    //模板名称

    public abstract void generate();

    public abstract void generate(Boolean isAsync);

    public abstract void generateByTemplate();

    public abstract TemplateFile buildTemplateFile();

    public AutoGenerate setTemplatePath(String templatePath){
        this.templatePath = templatePath;
        return this;
    }

    public AutoGenerate setFileSavePath(String fileSavePath){
        this.fileSavePath = fileSavePath;
        return this;
    }

    public AutoGenerate setTemplateName(String templateName){
        this.templateName = templateName;
        return this;
    }

}
