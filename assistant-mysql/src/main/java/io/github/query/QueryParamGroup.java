package io.github.query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class QueryParamGroup {


    @Value("#{${assistant.mysql.query:{}}}")
    private Map<String,String> query;

    public String getQueryParams(String queryType){

        return Optional.ofNullable(query.get(queryType)).orElse("");
    }
}
