package io.github.exception.handler.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.*;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ControllerAdvice
public @interface AssistantControllerAdvice {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = ControllerAdvice.class,attribute = "basePackages")
    String[] values() default {};


    @AliasFor(annotation = ControllerAdvice.class,attribute = "value")
    String[] basePackages() default {};

    @AliasFor(annotation = ControllerAdvice.class,attribute = "basePackageClasses")
    Class<?>[] basePackageClasses() default {};

    @AliasFor(annotation = ControllerAdvice.class,attribute = "assignableTypes")
    Class<?>[] assignableTypes() default {};

    @AliasFor(annotation = ControllerAdvice.class,attribute = "annotations")
    Class<? extends Annotation>[] annotations() default {};
}
