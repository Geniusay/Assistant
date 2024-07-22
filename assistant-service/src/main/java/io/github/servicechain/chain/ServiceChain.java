package io.github.servicechain.chain;


import lombok.Getter;
import lombok.Setter;

public class ServiceChain<T>{
    @Getter
    private int order = 5;

    @Getter
    private AbstractFilterChain<T> chain;

    private boolean isIgnore = false;

    private ServiceChain() {
    }

    private ServiceChain<?> next;
    public ServiceChain(int order, AbstractFilterChain<T> chain, ServiceChain<?> next) {
        this.order = order;
        this.chain = chain;
        this.next = next;
    }

    public ServiceChain(int order, AbstractFilterChain<T> chain, boolean isIgnore) {
        this.order = order;
        this.chain = chain;
        this.isIgnore = isIgnore;
    }

    public ServiceChain(ChainBluePrint bluePrint){
        this.order = bluePrint.getOrder();
        this.chain = bluePrint.getChain();
        this.isIgnore =bluePrint.isIgnore();
    }

    public ServiceChain(AbstractFilterChain<T> chain, ServiceChain<?> next) {
        this.chain = chain;
        this.next = next;
    }

    public void setNext(ServiceChain<?> next){
        this.next = next;
    }

    public ServiceChain<?> next(){
        return next;
    }

    public boolean doFilter(T value){
        return chain.filter(value);
    }

    public void setIgnore(boolean ignore) {
        isIgnore = ignore;
    }


    public boolean isIgnore() {
        return this.isIgnore;
    }

    public static ServiceChain<?> empty(){
        return new ServiceChain();
    }
}
