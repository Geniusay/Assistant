package io.github.util.collection.map;

import java.util.ArrayList;
import java.util.List;

public class MapNode<T> {

    private T data;

    private final List<MapNode<T>> linkNode;

    public MapNode(T data, List<MapNode<T>> linkNode) {
        this.data = data;
        this.linkNode = linkNode;
    }

    public MapNode(T data) {
        this.data = data;
        this.linkNode = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public List<MapNode<T>> getLinkNode() {
        return linkNode;
    }

    public void addLinkNode(MapNode<T> node){
        linkNode.add(node);
    }

    public void setData(T data) {
        this.data = data;
    }
}
