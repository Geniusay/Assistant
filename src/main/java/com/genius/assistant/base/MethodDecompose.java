package com.genius.assistant.base;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Genius
 * @date 2023/03/20 21:16
 **/
@Component
public class MethodDecompose {


    private static final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
    /*
    * 利用asm对字节码进行分析，获取方法的参数名
    * @param ClassPath 类的全路径
    * @param methodName 方法名
     */
    public List<String> getMethodParamName(String ClassPath,String methodName){
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.getOrNull(ClassPath);
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute attribute =  methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) attribute.getAttribute(LocalVariableAttribute.tag);
            List<String> paramNames = new ArrayList<>();
            for (int i = 0; i < ctMethod.getParameterTypes().length; i++) {
                paramNames.add(attr.variableName(i));
            }
            return paramNames;
        }catch (NotFoundException e) {
            throw new RuntimeException("不存在的类");
        }
    }

    public List<String> getMethodParamNameByDiscover(Method method){
        if(method != null){
            return List.of(Objects.requireNonNull(discoverer.getParameterNames(method)));
        }else{
            throw new RuntimeException("不存在的方法");
        }
    }
}
