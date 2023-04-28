package io.github.util;


import io.github.util.path.PathUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Genius
 **/
@SpringBootTest
public class PathUtilsTest {

    @Test
    public void testSmartPath(){
        System.out.println(PathUtils.smartFilePath("a/","\\b","c\\","/d"));
        System.out.println(PathUtils.smartFilePath('\\',"a//","\\b","c\\","/d"));

    }
}
