package io.github.geniusay.service.servicechain;

import io.github.pojo.TaskDO;
import io.github.servicechain.ServiceChainFactory;
import io.github.servicechain.bootstrap.ReturnType;
import io.github.servicechain.bootstrap.ServiceChainBootstrap;
import io.github.servicechain.chain.ServiceChainProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ServiceChainTest {

    @Resource
    private ServiceChainFactory factory;

    @Test
    public void testCommonChain(){
        String str = "12345";
        String empty = "";

        ServiceChainBootstrap<String> bootstrap = factory.get("string");
        System.out.println(bootstrap.execute(str));
        System.out.println(bootstrap.execute(empty));
    }

    @Test
    public void testSupplierChain(){
        TaskDO task = new TaskDO(12, "taskHello", "hello");
        TaskDO empty = new TaskDO(12, "", "");
        TaskDO errorTask = new TaskDO(12, "1234567890", "error");

        ServiceChainBootstrap<TaskDO> bootstrap = factory
                .<TaskDO>get("task").
                supplierMap(Map.of(1, TaskDO::getTaskName));

        System.out.println(bootstrap.execute(task));
        System.out.println(bootstrap.execute(empty));
        System.out.println(bootstrap.execute(errorTask));
    }

    @Test
    public void testIgnoreChain(){
        TaskDO task = new TaskDO(12, "taskHello", "hello");
        TaskDO empty = new TaskDO(12, "", "");
        TaskDO errorTask = new TaskDO(12, "1234567890", "error");

        ServiceChainBootstrap<TaskDO> bootstrap = factory
                .<TaskDO>get("ignoreTaskException")
                .returnType(ReturnType.THROW)
                .supplierMap(Map.of(1, TaskDO::getTaskName));

        System.out.println(bootstrap.execute(task));
        System.out.println(bootstrap.execute(empty));
        System.out.println(bootstrap.execute(errorTask));
    }

    @Test
    public void testCallbackChain(){
        TaskDO task = new TaskDO(12, "taskHello", "hello");
        TaskDO empty = new TaskDO(12, "", "");
        TaskDO errorTask = new TaskDO(12, "1234567890", "error");

        ServiceChainBootstrap<TaskDO> bootstrap = factory
                .<TaskDO>get("ignoreTaskException")
                .returnType(ReturnType.THROW)
                .supplierMap(Map.of(1, TaskDO::getTaskName));

        System.out.println(bootstrap.execute(task));
        System.out.println(bootstrap.execute(empty));
        System.out.println(bootstrap.execute(errorTask));
    }
}
