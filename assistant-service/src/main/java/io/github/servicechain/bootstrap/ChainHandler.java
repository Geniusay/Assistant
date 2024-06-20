package io.github.servicechain.bootstrap;

public interface ChainHandler<T,R> {
    R execute(T t);
}
