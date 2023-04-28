package io.github.serviceHelper.login;


import io.github.servicehelper.login.service.FormLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author Genius
 * @date 2023/04/13 01:11
 **/
@SpringBootTest
public class FormLoginServiceTest {

    @Autowired
    private FormLoginService formLoginService;

    @Test
    public void test() {
        formLoginService.login(Map.of("username","Genius","password","123456"));
    }
}
