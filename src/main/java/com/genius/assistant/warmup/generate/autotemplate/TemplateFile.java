package com.genius.assistant.warmup.generate.autotemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Genius
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TemplateFile implements Serializable {
    //文件后缀
    private String fileSuffix;

}
