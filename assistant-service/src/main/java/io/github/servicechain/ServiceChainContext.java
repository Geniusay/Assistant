package io.github.servicechain;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceChainContext {

    private static final ThreadLocal<ServiceChainContext> CONTEXT = ThreadLocal.withInitial(ServiceChainContext::new);

    private List<Object> resultRecord;
    private final Map<String,Object> data;

    private ServiceChainContext() {
        resultRecord = new ArrayList<>();
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

    public static void recordSet(String key, Object value){
        context().resultRecord.add(value);
        set(key, value);
    }
    public static Object set(String key, Object value){
        return context().data.put(key, value);
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

    public static Object getPreSetResult(){
        return context().resultRecord.isEmpty()?null: context().resultRecord.get(context().resultRecord.size()-1);
    }
}
