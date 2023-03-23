package com.genius.assistant.warmup.generate.autotemplate;

import com.genius.assistant.common.apiJs.ApiJs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Genius
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiJsTemplateFile extends TemplateFile{

    private final String fileSuffix = ".js";

    private String fileAxiosPath;

    private List<ApiJs> apiJsList = new ArrayList<>();
}
