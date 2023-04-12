package com.genius.assistant.servicehelper;

import com.genius.assistant.common.NodeResult;
import com.genius.assistant.common.Result;

public interface ServiceNode<T> {

    NodeResult<T> execute();

}
