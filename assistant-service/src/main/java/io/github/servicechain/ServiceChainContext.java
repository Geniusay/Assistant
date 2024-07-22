package io.github.servicechain;

import org.apache.tomcat.jni.Time;

import java.util.HashMap;
import java.util.Map;

public class ServiceChainContext {

    private static final ThreadLocal<ServiceChainContext> CONTEXT = ThreadLocal.withInitial(ServiceChainContext::new);

    private final Map<String,Object> data;

    private ServiceChainContext() {
        this.data = new HashMap<>();
    }

    private static ThreadLocal<ServiceChainContext> getContext() {
        return CONTEXT;
    }

    public static ServiceChainContext context() {
        return getContext().get();
    }

    public static Object get(String key){
        return context().data.get(key);
    }

    public static void set(String key, Object value){
        context().data.put(key, value);
    }

    public static void setAll(Map<String,Object> data){
        context().data.putAll(data);
    }

    public static void remove(String key){
        context().data.remove(key);
    }

    public static void clear(){
        context().data.clear();
    }

    public <T> T get(String key, Class<T> type){
        return type.cast(get(key));
    }

    public static void extendTo(ServiceChainContext context){
        ServiceChainContext.setAll(context.data);
    }
}
