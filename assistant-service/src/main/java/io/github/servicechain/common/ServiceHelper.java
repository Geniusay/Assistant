package io.github.servicechain.common;

import io.github.servicechain.ServiceChainContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceHelper {

    public Object preResult(){
        return ServiceChainContext.getPreSetResult();
    }
}
