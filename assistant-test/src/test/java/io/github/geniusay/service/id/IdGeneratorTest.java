package io.github.geniusay.service.id;

import io.github.id.IdGenerator;
import io.github.id.IdGeneratorException;
import io.github.id.UUIDGenerator;
import io.github.id.snowflake.SnowflakeGenerator;
import io.github.id.snowflake.SnowflakeRegisterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class IdGeneratorTest {

    @Resource
    SnowflakeGenerator snowflakeGenerator;
    @Resource
    UUIDGenerator uuidGenerator;

    @Test
    public void testSnowflakeGenerator() throws IdGeneratorException {
        for (int i = 0; i < 100; i++) {
            System.out.println(snowflakeGenerator.generate());
        }
    }

    @Test
    public void testUUIDGenerator(){
        for (int i = 0; i < 100; i++) {
            System.out.println(uuidGenerator.generate());
        }
    }
}
