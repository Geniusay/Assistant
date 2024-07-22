package io.github.servicechain.bootstrap;

import io.github.servicechain.chain.ServiceChain;

import java.util.Map;
import java.util.function.Function;

public class ServiceChainHandler<T> implements ChainHandler<T,Boolean>{

    private ReturnType returnType;

    private Map<Integer, Function> supplierMap;

    private Map<Integer, SuccessCallback> successCallbackMap;

    private Map<Integer, FailCallback> failCallbackMap;

    private ServiceChain<?> serviceChain;

    private final Function<T,?> originSupplier = (obj)->obj;

    public static ServiceChainBootstrap bootstrap(){
        return new ServiceChainBootstrap();
    }

    protected ServiceChainHandler(ServiceChainBootstrap bootstrap){
        this.returnType = bootstrap.getReturnType();
        this.supplierMap = bootstrap.getSupplierMap();
        this.successCallbackMap = bootstrap.getSuccessCallbackMap();
        this.failCallbackMap = bootstrap.getFailCallbackMap();
        this.serviceChain = bootstrap.getServiceChain();
    }


    private boolean doFilter(T obj,ServiceChain chain,int number){
        try {
            Function<T,?> function = supplierMap.getOrDefault(number,originSupplier);
            boolean res = chain.doFilter(function.apply(obj));
            callback(res,number);
            return res||chain.isIgnore();
        }catch (Exception e){
            callback(false,number);
            if (chain.isIgnore()||ReturnType.BOOLEAN.equals(returnType)) {
                return chain.isIgnore();
            }
            throw e;
        }
    }

    private void callback(Boolean res,int number){
        ServiceChainCallback callback = res? successCallbackMap.get(number): failCallbackMap.get(number);
        if(callback!=null){
            callback.callback();
        }
    }

    @Override
    public Boolean execute(T obj) {
        ServiceChain<?> temp = serviceChain;
        int number = 0;
        while (temp != null) {
            number++;
            try {
                if(doFilter(obj,temp,number)){
                    temp = temp.next();
                }else{
                    return false;
                }
            }catch (Exception e){
                throw e;
            }
        }
        return true;
    }
}
