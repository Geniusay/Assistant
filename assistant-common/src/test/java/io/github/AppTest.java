package io.github;

import io.github.common.logger.CommonLogger;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest
{
    @Resource
    CommonLogger commonLogger;
    @Test
    public void testLogger(){
        commonLogger.info("test %s","jett");
    }
}
