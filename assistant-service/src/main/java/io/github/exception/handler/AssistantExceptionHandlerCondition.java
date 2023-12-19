package io.github.exception.handler;

import io.github.exception.handler.annotation.AssistantControllerAdvice;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * assistant异常处理开启条件类
 * 使用方法:
 * 1,关闭某个异常处理类
 *      assistant.service.exception.handler.disables:xxxx,xxxx
 * 2,不配置则默认全部开启
 */
@Component
public class AssistantExceptionHandlerCondition  implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        // 获取@ControllerAdvice注解的属性
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(AssistantControllerAdvice.class.getName()));

        if (attributes != null) {
            List disables = environment.getProperty("assistant.service.exception.handler.disables", List.class);
            String value = attributes.getString("value");
            return disables==null||!disables.contains(value);
        }
        return false;
    }
}
