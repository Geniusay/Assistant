package io.github.chain;

import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import java.util.List;
import java.util.Objects;

@Chain("objectNotEmpty")
public class ObjectNotEmptyFilterChain extends AbstractFilterChain<Object> {

    @Override
    public List<ServicePoint> servicePoints(){
        return List.of(
                new ServicePoint("hello",5),
                new ServicePoint("world",4)
        );
    }

    @Override
    public boolean filter(Object value) {
        return !Objects.isNull(value);
    }
}
