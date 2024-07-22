package io.github.servicechain.chain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class ChainBluePrint implements Comparable<ChainBluePrint>{

    private final boolean isIgnore;

    private final AbstractFilterChain chain;

    private final int order;

    public ChainBluePrint(boolean isIgnore, AbstractFilterChain chain, int order) {
        this.isIgnore = isIgnore;
        this.chain = chain;
        this.order = order;
    }

    @Override
    public int compareTo(ChainBluePrint o) {
        return o.getOrder() - this.getOrder() ;
    }

    public static ServiceChain<?> buildServiceChain(List<ChainBluePrint> bluePrints){
        ServiceChain<?> head = null;

        for (int i = bluePrints.size()-1; i >=0; i--) {
            ServiceChain<Object> temp = new ServiceChain<>( bluePrints.get(i));
            temp.setNext(head);
            head = temp;
        }
        return head;
    }
}
