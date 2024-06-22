package io.github.geniusay.service.servicechain;

import io.github.chain.ObjectNotEmptyFilterChain;
import io.github.chain.StringNotEmptyFilterChain;
import io.github.pojo.TaskDO;
import io.github.servicechain.ServiceChainFactory;
import io.github.servicechain.bootstrap.ReturnType;
import io.github.servicechain.bootstrap.ServiceChainBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
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
        boolean res = factory.get("string").execute(str);
    }

    @Test
    public void testSupplierChain(){
        TaskDO task = new TaskDO(12, "taskHello", "hello");
        TaskDO empty = new TaskDO(12, "", "");
        TaskDO errorTask = new TaskDO(12, "1234567890", "error");

        ServiceChainBootstrap bootstrap = factory
                .get("task")
                .<TaskDO>supplierMap(Map.of(1, TaskDO::getTaskName));

        System.out.println(bootstrap.execute(task));
        System.out.println(bootstrap.execute(empty));
        System.out.println(bootstrap.execute(errorTask));
    }

    @Test
    public void testIgnoreChain(){
        TaskDO task = new TaskDO(12, "taskHello", "hello");
        TaskDO empty = new TaskDO(12, "", "");
        TaskDO errorTask = new TaskDO(12, "1234567890", "error");

        ServiceChainBootstrap bootstrap = factory
                .get("ignoreTaskException")
                .returnType(ReturnType.THROW)
                .<TaskDO>supplierMap(Map.of(1, TaskDO::getTaskName))
                .failCallbackMap(Map.of(
                        1, () -> System.out.println("taskName is empty")
                ))
                .successCallbackMap(Map.of(
                        1, () -> System.out.println("taskName not empty")
                ));

        System.out.println(bootstrap.execute(task));
        System.out.println(bootstrap.execute(empty));
        System.out.println(bootstrap.execute(errorTask));
    }

    @Test
    public void testCallbackChain(){
        TaskDO task = new TaskDO(12, "taskHello", "hello");
        TaskDO empty = new TaskDO(12, "", "");
        TaskDO errorTask = new TaskDO(12, "1234567890", "error");

        ServiceChainBootstrap bootstrap = factory
                .get("taskException")
                .returnType(ReturnType.THROW)
                .<TaskDO>supplierMap(Map.of(1, TaskDO::getTaskName));

        System.out.println(bootstrap.execute(task));
        System.out.println(bootstrap.execute(empty));
        System.out.println(bootstrap.execute(errorTask));
    }

    @Test
    public void testBuildChain(){
        boolean res = factory.bootstrap()
                .next(new ObjectNotEmptyFilterChain(), new StringNotEmptyFilterChain())
                .next(factory.getChain("Exception"), true)
                .next(factory.getChain("taskCheck"))
                .<TaskDO>supplierMap(
                        Map.of(2, TaskDO::getTaskName)
                ).execute(new TaskDO(12, "taskHello", "hello"));
        System.out.println(res);
    }
}
