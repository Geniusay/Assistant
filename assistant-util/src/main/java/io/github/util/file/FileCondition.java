package io.github.util.file;

import java.nio.file.Path;

@FunctionalInterface
public interface FileCondition {
    boolean condition(Path path);
}
