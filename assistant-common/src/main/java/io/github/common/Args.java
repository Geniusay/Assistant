package io.github.common;

import java.util.List;

public class Args {

    private final List<Object> args;

    private Args(List<Object> args) {
        this.args = args;
    }

    private Args(Object... args){
        this.args = List.of(args);
    }

    public static Args of(Object... args){
        return new Args(args);
    }

    public static Object[] args(Object... args){
        return args;
    }

    public List<Object> argList() {
        return args;
    }

    public Object[] args(){
        return args.toArray();
    }

    public Object get(int index){
        return args.get(index);
    }
}
