package io.github.geniusay.service.exception;

import io.github.exception.handler.CommonExceptionHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ExceptionHandlerTest {

    @Resource
    ApplicationContext context;

    @Test
    public void testInitHandlerCondition() {
        Map<String, Object> beansWithControllerAdvice = context.getBeansWithAnnotation(ControllerAdvice.class);

        int numberOfControllerAdviceBeans = beansWithControllerAdvice.size();

        System.out.println("Number of beans with @ControllerAdvice: " + numberOfControllerAdviceBeans);
    }

}
