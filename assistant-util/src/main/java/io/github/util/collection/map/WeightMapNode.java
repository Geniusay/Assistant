package io.github.util.collection.map;

import java.util.List;

public class WeightMapNode<T> extends MapNode<T>{

    private long weight;

    public interface WeightCompute{
        long weight();
    }

    private WeightCompute weightCompute;

    private final WeightCompute DEFAULT_COMPUTE = ()-> this.weight;

    public WeightMapNode(long weight, T data, List<MapNode<T>> linkNode) {
        super(data, linkNode);
        this.weight = weight;
        this.weightCompute = DEFAULT_COMPUTE;
    }

    public WeightMapNode(T data, long weight) {
        super(data);
        this.weight = weight;
        this.weightCompute = DEFAULT_COMPUTE;
    }

    public WeightMapNode(T data, long weight, WeightCompute weightCompute) {
        super(data);
        this.weight = weight;
        this.weightCompute = weightCompute;
    }

    public WeightMapNode(T data, WeightCompute weightCompute) {
        super(data);
        this.weight = 0;
        this.weightCompute = weightCompute;
    }

    public WeightMapNode(T data) {
        super(data);
        this.weight = 0;
        this.weightCompute = DEFAULT_COMPUTE;
    }

    public long getWeight() {
        return weightCompute.weight();
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void setWeightCompute(WeightCompute weightCompute) {
        this.weightCompute = weightCompute;
    }
}
