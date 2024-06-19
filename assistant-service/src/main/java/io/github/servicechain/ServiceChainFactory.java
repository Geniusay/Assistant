package io.github.servicechain;

import io.github.servicechain.chain.AbstractFilterChain;
import io.github.servicechain.chain.ServiceChain;
import io.github.servicechain.chain.ServiceChainProvider;
import io.github.servicechain.chain.ServicePointServiceChainProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceChainFactory {
    private Map<String, AbstractFilterChain> chainMap;

    private Map<String, ServiceChain> serviceChainMap;

    @Autowired
    public ServiceChainFactory(ApplicationContext applicationContext){
        chainMap =  applicationContext.getBeansOfType(AbstractFilterChain.class);
        serviceChainMap = new HashMap<>();
        applicationContext.getBeansOfType(ServiceChainProvider.class).forEach(
                (name, serviceChainProvider) -> serviceChainMap.putAll(serviceChainProvider.provide(chainMap))
        );
    }

    public AbstractFilterChain<?> getChain(String name){
        return chainMap.get(name);
    }

    public ServiceChain<?> getServiceChain(String serviceName){
        return serviceChainMap.get(serviceName);
    }
}
