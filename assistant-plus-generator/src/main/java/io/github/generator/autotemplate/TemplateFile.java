package io.github.generator.autotemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *    Genius
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TemplateFile implements Serializable {
    //文件后缀
    private String fileSuffix;

}
