package io.github.common.msgbuilder;

/**
 *   2023/10/13
 *    xiaochun
 */
public interface MsgBuilder {
    MsgBuilder build(String key,Object data);

    String done();
}
