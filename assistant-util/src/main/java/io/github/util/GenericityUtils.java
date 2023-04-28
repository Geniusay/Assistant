package io.github.util;

/**
 * @author Genius
 **/
// 泛型工具类
public class GenericityUtils {

    // 判断对象是否是某个类的实例
    public static <T> boolean instanceofFunc(Object obj,Class<T> b){
        boolean result;
        if (obj == null) {
            result = false;
        } else {
            try {
                T temp=b.cast(obj);
//                T temp= (T) obj; // checkcast
                result = true;
            } catch (ClassCastException e) {
                result = false;
            }
        }
        return result;

    }
}
