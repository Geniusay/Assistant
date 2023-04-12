package com.genius.assistant.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Genius
 * @date 2023/04/13 01:22
 **/
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class NodeResult<T> extends Result<T>{
    enum NodeType{
        /**
         * 服务节点
         */
        SERVICE,
        /**
         * 服务链节点
         */
        SERVICE_CHAIN
    }
    enum NodeStatus{
        /**
         * 服务节点
         */
        SUCCESS,
        /**
         * 服务链节点
         */
        FAIL
    }

    private NodeStatus nodeStatus;  // 节点状态
    private NodeType nodeType;      // 节点类型

    public boolean isSUCCESS(){
        return NodeStatus.SUCCESS.equals(this.getNodeStatus());
    }

}
