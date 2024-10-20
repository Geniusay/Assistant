package io.github.util.collection.map.dag;

import io.github.util.collection.map.MapNode;
import io.github.util.collection.map.WeightMapNode;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 工作图节点
 */
public class WorkWeightMapNode<T> extends WeightMapNode<T> {


    public interface WorkHandler{
        Object work(Object data);
    }

    private static final int NONE = 0;
    private static final int READY = 1;
    private static final int WORKING = 0x02;

    private static final int WAITING = 0x04;
    private static final int COMPLETING = 0x08;
    private static final int ERROR = 0x10;
    private static final WorkHandler DEFAULT = (obj)-> null;

    private WorkHandler workHandler = DEFAULT;

    private volatile int workStatus = NONE;

    private final ReentrantLock workLock = new ReentrantLock();

    public WorkWeightMapNode(long weight, T data, List<MapNode<T>> linkNode) {
        super(weight, data, linkNode);
    }

    public WorkWeightMapNode(T data, long weight) {
        super(data, weight);
    }

    public WorkWeightMapNode(T data, WeightMapNode.WeightCompute weightCompute, WorkHandler workHandler) {
        super(data, weightCompute);
        this.workHandler = workHandler;
    }

    public WorkWeightMapNode(T data, WorkHandler workHandler) {
        super(data);
        this.workHandler = workHandler;
    }

    public WorkWeightMapNode(T data) {
        super(data);
    }

    public void setWorkHandler(WorkHandler workHandler) {
        this.workHandler = workHandler;
    }

    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus){
        if(workStatus>ERROR || workStatus<NONE || pop_count(workStatus)!=1){
            throw new RuntimeException("error workStatus value");
        }
        this.workStatus = workStatus;
    }

    public static int pop_count(int x) {
        x = (x&0x5555)+((x>>1)&0x5555);
        x = (x&0x3333)+((x>>2)&0x3333);
        x = (x&0x0f0f)+((x>>4)&0x0f0f);
        x = (x&0x00ff)+((x>>8)&0x00ff);
        return x;
    }


    public Object work(Object obj){
        if(equalsStatus(READY)){
            try {
                workLock.lock();
                if(equalsStatus(READY)){
                    this.workStatus = WORKING;
                    Object res = workHandler.work(obj);
                    this.workStatus = COMPLETING;
                    return res;
                }
            }catch (Exception e){
                this.workStatus = ERROR;
                throw e;
            }finally {
                workLock.unlock();
            }
        }
        throw new RuntimeException("The node status must be ready to run");
    }

    private boolean equalsStatus(int status){
        return (this.workStatus & status)!=0;
    }

    public static void main(String[] args) {
        System.out.println(pop_count(3));
        System.out.println(pop_count(2));
        System.out.println(pop_count(37));
    }
}
