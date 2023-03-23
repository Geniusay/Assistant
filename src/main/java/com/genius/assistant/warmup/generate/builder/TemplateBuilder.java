package com.genius.assistant.warmup.generate.builder;

import com.genius.assistant.warmup.generate.autotemplate.TemplateFile;

public interface TemplateBuilder <T extends TemplateFile> {
    T build();

}
