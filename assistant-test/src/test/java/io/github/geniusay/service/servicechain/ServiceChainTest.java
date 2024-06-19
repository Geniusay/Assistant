package io.github.geniusay.service.servicechain;

import io.github.servicechain.ServiceChainFactory;
import io.github.servicechain.chain.ServiceChainProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ServiceChainTest {

    @Resource
    private ServiceChainFactory factory;

    @Test
    public void testGetChain(){
        System.out.println(factory.getServiceChain("test"));
    }
}
