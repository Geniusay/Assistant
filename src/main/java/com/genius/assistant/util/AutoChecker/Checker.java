package com.genius.assistant.util.AutoChecker;

import java.util.function.Supplier;

@FunctionalInterface
public interface Checker<T> {
    boolean goCheck(T obj);
}
