package io.github.servicechain.chain;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class ServicePointServiceChainProvider implements ServiceChainProvider{

    @Resource
    private ApplicationContext context;

    @Override
    public Map<String, ServiceChain<?>> provide(Map<String, AbstractFilterChain> map) {
        Map<String,ServiceChain<?>> res =  new HashMap<>();
        Map<String, PriorityQueue<ServiceChain<?>>> priorityQueueMap = new HashMap<>();

        map.forEach(
                (name, filterChain) ->{
                    List<AbstractFilterChain.ServicePoint> servicePoints = filterChain.servicePoints();
                    for (AbstractFilterChain.ServicePoint point : servicePoints) {
                        int order = point.getOrder();
                        String serviceName = point.getServiceName();
                        if (!priorityQueueMap.containsKey(serviceName)) {
                            priorityQueueMap.put(serviceName,new PriorityQueue<>());
                        }
                        ServiceChain<?> serviceChain = new ServiceChain<>(order, filterChain, null);
                        serviceChain.setChainName(name);
                        serviceChain.setIgnore(point.isIgnore());
                        priorityQueueMap.get(serviceName).add(serviceChain);
                    }
                }
        );

        priorityQueueMap.forEach(
                (serviceName, priorityQueue)->{
                    ServiceChain<?> head = null;
                    for (ServiceChain<?> serviceChain : priorityQueue) {
                        serviceChain.setNext(head);
                        head = serviceChain;
                    }
                    res.put(serviceName,head);
                }
        );
        return res;
    }
}
