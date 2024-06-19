package io.github.servicechain.chain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@ConfigurationProperties(prefix = "assistant.service-chain")
public class YamlServiceChainProvider implements  ServiceChainProvider{

    private Map<String, String> serviceMap = Collections.emptyMap();

    private static final String IGNORE = "IGNORE";
    @Override
    public Map<String, ServiceChain<?>> provide(Map<String, AbstractFilterChain> map) {
        Map<String,ServiceChain<?>> res = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+)\\[(\\w+)]");
        serviceMap.forEach(
            (serviceName,str)->{
                String[] chains = str.split("->");
                int order = 0;
                ServiceChain<?> head = null;
                for (int i=chains.length-1;i>=0;i--) {
                    String chain = chains[i];
                    ServiceChain<?> temp;
                    if(chain.contains("[")){
                        Matcher matcher = pattern.matcher(chain);
                        if(matcher.find()){
                            String chainName = matcher.group(1);
                            temp = new ServiceChain<>(order++,map.get(chainName),null);
                            if(IGNORE.equalsIgnoreCase(matcher.group(2))){
                                temp.setIgnore(true);
                            }
                            temp.setChainName(chainName);
                        }else{
                            continue;
                        }
                    }else{
                        temp = new ServiceChain<>(order++,map.get(chain),null);
                        temp.setChainName(chain);
                    }
                    temp.setNext(head);
                    head = temp;
                }
                res.put(serviceName,head);
            }
        );
        return res;
    }
}
