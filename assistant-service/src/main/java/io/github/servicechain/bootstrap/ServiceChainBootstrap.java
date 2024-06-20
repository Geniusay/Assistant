package io.github.servicechain.bootstrap;

import io.github.servicechain.chain.AbstractFilterChain;
import io.github.servicechain.chain.ServiceChain;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class ServiceChainBootstrap<T> {

    private ReturnType returnType =  ReturnType.BOOLEAN;

    private Map<Integer, Function<T,?>> supplierMap = new HashMap<>();

    private Map<Integer, SuccessCallback> successCallbackMap = new HashMap<>();

    private Map<Integer, FailCallback> failCallbackMap = new HashMap<>();

    private ServiceChain<?> serviceChain;

    private ServiceChain<?> tempChain;

    public ServiceChainBootstrap<T> next(AbstractFilterChain<?> chain,boolean isIgnore){
        ServiceChain<?> tempServiceChain = new ServiceChain<>(chain, null);
        tempServiceChain.setIgnore(isIgnore);
        if(serviceChain == null){
            serviceChain = tempServiceChain;
            tempChain = serviceChain;
        }else{
            tempChain.setNext(tempServiceChain);
            tempChain = tempServiceChain;
        }
        return this;
    }

    public ServiceChainBootstrap<T> next(AbstractFilterChain<?> chain){
        return this.next(chain,false);
    }

    public ServiceChainBootstrap<T> next(AbstractFilterChain<?>...chains){
        for (AbstractFilterChain<?> chain : chains) {
            this.next(chain,false);
        }
        return this;
    }

    public  ServiceChainBootstrap<T> serviceChain(ServiceChain<?> serviceChain){
        this.serviceChain = serviceChain;
        return this;
    }

    public ServiceChainBootstrap<T> supplierMap(Map<Integer, Function<T,?>> supplierMap){
        this.supplierMap.putAll(supplierMap);
        return this;
    }

    public ServiceChainBootstrap<T> successCallbackMap(Map<Integer, SuccessCallback> successCallbackMap){
        this.successCallbackMap.putAll(successCallbackMap);
        return this;
    }

    public ServiceChainBootstrap<T> failCallbackMap(Map<Integer, FailCallback> failCallbackMap){
        this.failCallbackMap.putAll(failCallbackMap);
        return this;
    }

    public ServiceChainBootstrap<T> returnType(ReturnType returnType){
        this.returnType = returnType;
        return this;
    }

    public <T> boolean execute(T obj){
        ServiceChainHandler<T> handler = new ServiceChainHandler<T>(this);
        return handler.execute(obj);
    }

}
