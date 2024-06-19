package io.github.servicechain.chain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ServiceChain<T> implements Comparable<ServiceChain<T>>{
    @Getter
    private int order = 5;

    @Getter
    private AbstractFilterChain<T> chain;

    @Getter
    @Setter
    private String chainName;

    private boolean isIgnore = false;

    private ServiceChain<?> next;

    public ServiceChain(AbstractFilterChain<T> chain) {
        this.chain = chain;
    }

    public ServiceChain(int order, AbstractFilterChain<T> chain, ServiceChain<?> next) {
        this.order = order;
        this.chain = chain;
        this.next = next;
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

    @Override
    public int compareTo(ServiceChain<T> o) {
        return this.getOrder() - o.getOrder() ;
    }
}
