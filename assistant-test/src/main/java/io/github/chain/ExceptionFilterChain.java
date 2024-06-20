package io.github.chain;

import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import java.util.List;

@Chain("Exception")
public class ExceptionFilterChain extends AbstractFilterChain<Object> {

    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(Object value) {
        throw new RuntimeException("ExceptionFilterChain error!!!!!!!!");
    }
}
