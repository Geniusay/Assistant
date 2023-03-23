package com.genius.assistant.warmup;

import com.genius.assistant.controller.TestController;
import com.genius.assistant.warmup.generate.AutoApiJsGenerate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.PushBuilder;
import java.util.List;

/**
 * @author Genius
 **/

/**
 * ApiJs自动生成器
 */
@SpringBootTest
public class AutoApiJsGenerateTest {

    @Autowired
    AutoApiJsGenerate autoApiJsGenerate;

    @Test
    public void testGenerateByJs(){
        autoApiJsGenerate.generate();
    }

    //根据模板生成
    @Test
    public void testGenerateByTemplate(){
        autoApiJsGenerate.generate();
    }

    //根据传统的Js生成
    @Test
    public void testGenerateJS(){
        autoApiJsGenerate.
                setIsJsMoodleGenerated(true).
                generate();
    }


    //自定义生成
    @Test
    public void testGenerateByDIY(){

        /*autoApiJsGenerate.
                setIsJsMoodleGenerated(true).               //是否使用传统Js模板
                filter(List.of(TestController.class)).      //过滤掉不需要生成js文件的controller
                setFileSavePath("E:\\Project\\Assistant\\src\\main\\resources\\js"). //文件保存区域
                generate(true); //是否异步生成*/



        autoApiJsGenerate
                .filter(List.of(TestController.class))
                .setTemplatePath("src/main/resources/templates/")//选择模板加载路径
                .setTemplateName("apiJs.java.vm")   //选择模板名字
                .setFileSavePath("E:\\Project\\Assistant\\src\\main\\resources\\js")
                .generate(true); //是否异步生成
    }
}
