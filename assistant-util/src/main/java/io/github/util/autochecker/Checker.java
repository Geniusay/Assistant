package io.github.util.autochecker;

@FunctionalInterface
public interface Checker<T> {
    boolean goCheck(T obj);
}
