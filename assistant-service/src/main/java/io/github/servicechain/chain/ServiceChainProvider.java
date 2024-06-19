package io.github.servicechain.chain;

import java.util.Map;

public interface ServiceChainProvider{
   Map<String,ServiceChain<?>> provide(Map<String, AbstractFilterChain> map);
}
