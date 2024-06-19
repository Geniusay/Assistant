package io.github.servicechain.annotation;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE) // 只能用于类上
@Retention(RetentionPolicy.RUNTIME)
public @interface Chain {
    String value();
}
