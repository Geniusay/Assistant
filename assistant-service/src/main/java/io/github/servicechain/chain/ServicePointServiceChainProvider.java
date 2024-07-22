package io.github.servicechain.chain;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class ServicePointServiceChainProvider implements ServiceChainProvider{

    @Resource
    private ApplicationContext context;


    @Override
    public Map<String, List<ChainBluePrint>> provideBluePrint(Map<String, AbstractFilterChain> map) {
        Map<String,PriorityQueue<ChainBluePrint>> bluePrintMap = new HashMap<>();
        map.forEach(
                (name, filterChain) ->{
                    List<AbstractFilterChain.ServicePoint> servicePoints = filterChain.servicePoints();
                    if(Objects.isNull(servicePoints)||servicePoints.isEmpty()){
                        return;
                    }
                    for (AbstractFilterChain.ServicePoint point : servicePoints) {
                        int order = point.getOrder();
                        String serviceName = point.getServiceName();
                        bluePrintMap.computeIfAbsent(serviceName,(k)->new PriorityQueue<>())
                                .add(new ChainBluePrint(point.isIgnore(),filterChain,order));
                    }
                }
        );
        Map<String,List<ChainBluePrint>> ans = new HashMap<>();
        bluePrintMap.forEach(
                (k,v)->{
                    ans.put(k,new ArrayList<>(v));
                }
        );
        return ans;
    }
}
