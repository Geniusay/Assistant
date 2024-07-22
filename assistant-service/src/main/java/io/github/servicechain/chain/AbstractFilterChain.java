package io.github.servicechain.chain;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public abstract class AbstractFilterChain<V> implements FilterChain<V>{

    public AbstractFilterChain() {
    }


    public List<ServicePoint> servicePoints(){
        return null;
    };

    @Getter
    static protected class ServicePoint{

        private String serviceName;

        private int order;

        private boolean isIgnore = false;

        public ServicePoint(String serviceName, int order) {
            this.serviceName = serviceName;
            this.order = order;
        }

        public ServicePoint(String serviceName) {
            this.serviceName = serviceName;
            this.order = 5;
        }

        public ServicePoint(String serviceName, int order, boolean isIgnore) {
            this.serviceName = serviceName;
            this.order = order;
            this.isIgnore = isIgnore;
        }
    }
}
