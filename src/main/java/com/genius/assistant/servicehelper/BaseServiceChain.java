package com.genius.assistant.servicehelper;


import com.genius.assistant.common.NodeResult;
import com.genius.assistant.common.Result;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.Objects;

/**
 * @author Genius
 * @date 2023/04/12 02:58
 **/
@AllArgsConstructor
@NoArgsConstructor
public class BaseServiceChain {

    private ServiceNode<Map> node;  // 下一个服务节点

    private BaseServiceChain nextChain;

    public BaseServiceChain(ServiceNode<Map> nextNode) {
        this.node = nextNode;
    }

    public BaseServiceChain bindNext(ServiceNode<Map> nextNode) {
        this.nextChain = new BaseServiceChain(nextNode);
        return this.nextChain;
    }

    public NodeResult<Map> run() {
        NodeResult<Map> result = null;
        if(nextChain!=null){
            result=node.execute();
            if (Objects.isNull(result) || result.isSUCCESS()) {
                this.nextChain.run();
            } else {
                return result;
            }
        }
        return result;
    }
}
