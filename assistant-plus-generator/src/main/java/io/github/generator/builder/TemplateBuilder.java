package io.github.generator.builder;


import io.github.generator.autotemplate.TemplateFile;

public interface TemplateBuilder <T extends TemplateFile> {
    T build();

}
