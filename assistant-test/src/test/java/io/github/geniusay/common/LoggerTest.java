package io.github.geniusay.common;

import io.github.common.logger.CommonLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LoggerTest {

    @Resource
    CommonLogger commonLogger;

    @Test
    public void testSlf4jLogger(){
        int[] prices = {6,1,3,2,4,7};
        Arrays.sort(prices);
    }
}
