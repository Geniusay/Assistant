package io.github.servicechain;

import io.github.servicechain.bootstrap.ServiceChainBootstrap;
import io.github.servicechain.bootstrap.ServiceChainHandler;
import io.github.servicechain.chain.AbstractFilterChain;
import io.github.servicechain.chain.ChainBluePrint;
import io.github.servicechain.chain.ServiceChain;
import io.github.servicechain.chain.ServiceChainProvider;
import io.github.servicechain.common.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceChainFactory {
    private final Map<String, AbstractFilterChain> chainMap;

    private final Map<String, ServiceChain> serviceChainMap;

    private final Map<String,List<ChainBluePrint>> chainBluePrintMap;

    @Resource
    private ServiceHelper helper;

    @Autowired
    public ServiceChainFactory(ApplicationContext applicationContext){
        chainMap =  applicationContext.getBeansOfType(AbstractFilterChain.class);
        serviceChainMap = new HashMap<>();
        chainBluePrintMap = new HashMap<>();
        applicationContext.getBeansOfType(ServiceChainProvider.class).forEach(
                (name, serviceChainProvider) -> {
                    Map<String, List<ChainBluePrint>> provideBluePrint = serviceChainProvider.provideBluePrint(chainMap);
                    chainBluePrintMap.putAll(provideBluePrint);
                    serviceChainMap.putAll(serviceChainProvider.provide(provideBluePrint));
                }
        );
    }

    public AbstractFilterChain<?> getChain(String name){
        return chainMap.get(name);
    }

    public ServiceChainBootstrap get(String serviceName){
        return get(serviceName,true);
    }

    public ServiceChainBootstrap get(String serviceName,boolean singleton){
        ServiceChain<?> serviceChain;
        if(singleton){
            serviceChain = serviceChainMap.get(serviceName);
        }else{
            serviceChain = ChainBluePrint.buildServiceChain(chainBluePrintMap.get(serviceName));
        }

        if(serviceChain == null){
            throw new RuntimeException("No service chain found for service: " + serviceName);
        }
        return ServiceChainHandler.bootstrap().serviceChain(serviceChain);
    }
    public ServiceChainBootstrap bootstrap(){
        return ServiceChainHandler.bootstrap();
    }

    public ServiceHelper helper(){
        return helper;
    }

}
