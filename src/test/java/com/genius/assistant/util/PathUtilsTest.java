package com.genius.assistant.util;

import com.genius.assistant.util.path.PathUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Genius
 * @date 2023/03/23 21:55
 **/
@SpringBootTest
public class PathUtilsTest {

    @Test
    public void testSmartPath(){
        System.out.println(PathUtils.smartFilePath("a/","\\b","c\\","/d"));
        System.out.println(PathUtils.smartFilePath('\\',"a//","\\b","c\\","/d"));

    }
}
