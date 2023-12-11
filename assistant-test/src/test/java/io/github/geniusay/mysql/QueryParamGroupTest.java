package io.github.geniusay.mysql;

import io.github.query.QueryParamGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class QueryParamGroupTest {

    @Resource
    QueryParamGroup queryParamGroup;

    @Test
    public void testQueryParamGroup(){
        System.out.println(queryParamGroup.getQueryParams("feedback_list", "t"));
    }
}
