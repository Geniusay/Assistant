package com.genius.assistant.warmup.generate.autotemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Genius
 * @date 2023/03/23 17:57
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class TemplateFile implements Serializable {
    //文件后缀
    private String fileSuffix;

}
