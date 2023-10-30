package io.github.common;

/**
 * @author Genius
 * @date 2023/10/17 01:30
 **/

/**
 * 安全Bag类，安全存放某个类
 * @param <T>
 */
public class SafeBag<T> {
    T data;

    public SafeBag(T data) {
        this.data = data;
    }

    public SafeBag() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data){
        this.data = data;
    }
}
