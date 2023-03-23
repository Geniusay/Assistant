package com.genius.assistant.util;

import com.genius.assistant.util.file.CommonFileUtils;
import com.genius.assistant.warmup.generate.autotemplate.TemplateFile;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;

/**
 * @author Genius
 **/
/*
 模板生成引擎，目前使用的是velocity，后期可以考虑使用freemarker
 */
@Component
public class VelocityUtils {

    private static Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

    public static  <T extends TemplateFile> VelocityContext getVelocityContext(String name, T templateFile){
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(name, templateFile);
        return velocityContext;
    }

    public static VelocityContext getVelocityContextByObject(String name, Object object){
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(name, object);
        return velocityContext;
    }

    /*
     * 通过模板生成文件
     * @param velocityContext
     * @param templateName 模板路径
     * @param loadPath 模板加载路径
     * @param filePath 生成文件路径包含文件名
     */
    public static void generateFileByVelocity(VelocityContext velocityContext, String templateName,
                                              String loadPath, String filePath) throws Exception {
       Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, loadPath);

        Template template = Velocity.getTemplate(templateName);
        File f = CommonFileUtils.createFile(filePath);

        try(FileWriter fileWriter = new FileWriter(f)){
            template.merge(velocityContext, fileWriter);
            logger.info("-----模板生成文件成功-----:{}",filePath);
        }catch (Exception e){
            throw e;
        }finally {
        }
    }
}
