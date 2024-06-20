package io.github.servicechain.bootstrap;

import io.github.servicechain.chain.AbstractFilterChain;
import io.github.servicechain.chain.ServiceChain;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Getter
public class ServiceChainBootstrap {

    private ReturnType returnType =  ReturnType.BOOLEAN;

    private Map<Integer, Supplier<?>> supplierMap = new HashMap<>();

    private Map<Integer, SuccessCallback> successCallbackMap = new HashMap<>();

    private Map<Integer, FailCallback> failCallbackMap = new HashMap<>();

    private ServiceChain<?> serviceChain;

    private ServiceChain<?> tempChain;

    public ServiceChainBootstrap next(AbstractFilterChain<?> chain,boolean isIgnore){
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

    public ServiceChainBootstrap next(AbstractFilterChain<?> chain){
        return this.next(chain,false);
    }

    public ServiceChainBootstrap next(AbstractFilterChain<?>...chains){
        for (AbstractFilterChain<?> chain : chains) {
            this.next(chain,false);
        }
        return this;
    }

    public  ServiceChainBootstrap serviceChain(ServiceChain<?> serviceChain){
        this.serviceChain = serviceChain;
        return this;
    }

    public ServiceChainBootstrap supplierMap(Map<Integer, Supplier<?>> supplierMap){
        this.supplierMap.putAll(supplierMap);
        return this;
    }

    public ServiceChainBootstrap successCallbackMap(Map<Integer, SuccessCallback> successCallbackMap){
        this.successCallbackMap.putAll(successCallbackMap);
        return this;
    }

    public ServiceChainBootstrap failCallbackMap(Map<Integer, FailCallback> failCallbackMap){
        this.failCallbackMap.putAll(failCallbackMap);
        return this;
    }

    public ServiceChainBootstrap returnType(ReturnType returnType){
        this.returnType = returnType;
        return this;
    }

    public boolean execute(Object obj){
        ServiceChainHandler handler = new ServiceChainHandler(this);
        return handler.execute(obj);
    }

}
