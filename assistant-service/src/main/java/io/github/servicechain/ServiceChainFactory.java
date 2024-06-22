package io.github.servicechain;

import io.github.servicechain.bootstrap.ServiceChainBootstrap;
import io.github.servicechain.bootstrap.ServiceChainHandler;
import io.github.servicechain.chain.AbstractFilterChain;
import io.github.servicechain.chain.ServiceChain;
import io.github.servicechain.chain.ServiceChainProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceChainFactory {
    private final Map<String, AbstractFilterChain> chainMap;

    private final Map<String, ServiceChain> serviceChainMap;

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


    public ServiceChainBootstrap get(String serviceName){
        ServiceChain<?> serviceChain = serviceChainMap.get(serviceName);
        if(serviceChain == null){
            throw new RuntimeException("No service chain found for service: " + serviceName);
        }
        return ServiceChainHandler.bootstrap().serviceChain(serviceChain);
    }

    public ServiceChainBootstrap bootstrap(){
        return ServiceChainHandler.bootstrap();
    }
}
