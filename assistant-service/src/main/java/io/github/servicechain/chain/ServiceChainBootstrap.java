package io.github.servicechain.chain;

import java.util.Map;
import java.util.function.Supplier;

public class ServiceChainBootstrap {

    private String returnType = "boolean";

    private Map<String, Supplier<?>> supplierMap;

    private String serviceName;

    private ServiceChain<?> serviceChain;

    public boolean execute(Object obj) {
        ServiceChain temp = serviceChain;
        Supplier<?> origin = ()-> obj;
        while (temp.next() != null) {
            Supplier<?> supplier = supplierMap.getOrDefault(temp.getChainName(), origin);
            try {
                boolean res = temp.doFilter(supplier.get());
                if(temp.isIgnore()||res){
                    temp = temp.next();
                }else{
                    return false;
                }
            }catch (Exception e){
                if(temp.isIgnore()){
                    temp = temp.next();
                    continue;
                }else{
                    if(returnType.equals("boolean")){
                        return false;
                    }
                    throw e;
                }
            }
        }
        return true;
    }
}
