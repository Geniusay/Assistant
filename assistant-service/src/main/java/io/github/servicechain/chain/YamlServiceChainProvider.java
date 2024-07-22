package io.github.servicechain.chain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@ConfigurationProperties(prefix = "assistant.service-chain")
public class YamlServiceChainProvider implements  ServiceChainProvider{

    private Map<String, String> serviceMap = Collections.emptyMap();

    private static final String IGNORE = "IGNORE";

    @Override
    public Map<String, List<ChainBluePrint>> provideBluePrint(Map<String, AbstractFilterChain> map) {
        Map<String,List<ChainBluePrint>> res = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+)\\[(\\w+)]");
        serviceMap.forEach(
                (serviceName,str)->{
                    String[] chains = str.split("->");
                    int order = 0;
                    List<ChainBluePrint> bluePrints = new ArrayList<>();
                    for (String chainName:chains) {
                        ChainBluePrint bluePrint;
                        boolean isIgnore = false;
                        if(chainName.contains("[")){
                            Matcher matcher = pattern.matcher(chainName);
                            if(matcher.find()){
                                chainName = matcher.group(1);
                                isIgnore = IGNORE.equalsIgnoreCase(matcher.group(2));

                            }else{
                                continue;
                            }
                        }
                        AbstractFilterChain chain = map.getOrDefault(chainName, null);
                        if(chain==null){
                            throw new RuntimeException("service name "+chainName+" not found");
                        }
                        bluePrint = ChainBluePrint.builder()
                                .isIgnore(isIgnore)
                                .chain(chain)
                                .order(order++).build();

                        bluePrints.add(bluePrint);
                    }
                    res.put(serviceName,bluePrints);
                }
        );
        return res;
    }

}
