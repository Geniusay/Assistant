package io.github.chain;

import io.github.servicechain.annotation.Chain;
import io.github.servicechain.annotation.MultiFilterFunc;
import io.github.servicechain.chain.MultiArgsFilterChain;

import java.util.Map;

@Chain("returnMap")
public class ReturnMapFilterChain extends MultiArgsFilterChain<Map> {

    @MultiFilterFunc
    public Map<String,String> get(String key,String value){
        return Map.of(key,value);
    }
}
