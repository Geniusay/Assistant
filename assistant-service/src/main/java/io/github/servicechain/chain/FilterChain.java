package io.github.servicechain.chain;

public interface FilterChain <V>{

    boolean filter(V value);
}
