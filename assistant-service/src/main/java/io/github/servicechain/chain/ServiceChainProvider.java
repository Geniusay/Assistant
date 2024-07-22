package io.github.servicechain.chain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ServiceChainProvider{

   Map<String, List<ChainBluePrint>> provideBluePrint(Map<String, AbstractFilterChain> map);

   default Map<String, ServiceChain<?>> provide(Map<String, List<ChainBluePrint>> bluePrintMap) {
      Map<String,ServiceChain<?>> res =  new HashMap<>();
      bluePrintMap.forEach(
              (name, bluePrints) -> {
                 res.put(name,ChainBluePrint.buildServiceChain(bluePrints));
              }
      );
      return res;
   }
}
