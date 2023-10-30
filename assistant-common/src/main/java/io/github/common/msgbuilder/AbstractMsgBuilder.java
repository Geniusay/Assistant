package io.github.common.msgbuilder;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *   2023/10/13
 *    xiaochun
 */
public abstract class AbstractMsgBuilder implements MsgBuilder{
    protected Map<String,Object> map;

    public AbstractMsgBuilder() {
        this.map = new HashMap<>();
    }

    @Override
    public AbstractMsgBuilder build(String key, Object data){
        if(data==null){
            return this;
        }
        map.put(key,data);
        return this;
    }

    @Override
    public String done(){
        return JSONObject.toJSONString(map);
    }
}
