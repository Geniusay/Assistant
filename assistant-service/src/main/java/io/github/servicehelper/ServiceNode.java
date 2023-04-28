package io.github.servicehelper;


import io.github.common.NodeResult;

public interface ServiceNode<T> {

    NodeResult<T> execute();

}
