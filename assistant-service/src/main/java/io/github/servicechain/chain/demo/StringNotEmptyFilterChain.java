package io.github.servicechain.chain.demo;

import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;
import io.github.util.StringUtils;

import java.util.List;

@Chain("stringNotEmpty")
public class StringNotEmptyFilterChain extends AbstractFilterChain<String> {

    @Override
    public List<ServicePoint> servicePoints() {
        return List.of(
                new ServicePoint("hello",2),
                new ServicePoint("world",5)
        );
    }

    @Override
    public boolean filter(String value) {
        return !StringUtils.isEmpty(value);
    }
}
