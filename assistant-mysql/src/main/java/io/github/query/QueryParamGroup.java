package io.github.query;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Data
@ConfigurationProperties(prefix = "assistant.mysql")
public class QueryParamGroup {

    private Map<String, List<String>> queryGroup = new HashMap<>();

    public List<String> getQueryParams(String queryType){

        return Optional.ofNullable(queryGroup.get(queryType)).orElse(List.of());
    }
}
