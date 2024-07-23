package io.github.servicechain.chain;

import io.github.servicechain.ServiceChainContext;
import io.github.servicechain.annotation.MultiFilterFunc;
import io.github.common.Args;

import java.lang.reflect.Method;
import java.util.Objects;

public abstract class MultiArgsFilterChain<T> extends AbstractFilterChain<Object[]> {

    private Method filterMethod;

    public MultiArgsFilterChain() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MultiFilterFunc.class) ) {
                filterMethod = method;
                break;
            }
        }
        if(filterMethod==null){
            throw new RuntimeException("not found MultiFilterFunc method");
        }
    }

    @Override
    public boolean filter(Object... value) {
        T result;
        try {
             result = (T) filterMethod.invoke(this, value);
            if(!Objects.isNull(result)){
                saveRes(result);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return nextLogic(result);
    }

    public boolean filter(Args args){
        return filter(args.args());
    }

    public String filterId(){
        return String.valueOf(this.hashCode());
    }
    public boolean nextLogic(T res){
        return !Objects.isNull(res);
    }

    private void saveRes(T result){
        ServiceChainContext.recordSet(filterId(),result);
    }
}
