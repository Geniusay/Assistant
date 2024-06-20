package io.github.chain;

import io.github.pojo.TaskDO;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;
import io.github.util.StringUtils;

import java.util.List;

@Chain("taskCheck")
public class TaskCheckFilterChain extends AbstractFilterChain<TaskDO> {


    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(TaskDO value) {
        if (!value.getTaskName().startsWith("task")) {
            return false;
        }
        if(StringUtils.isEmpty(value.getTaskDescription())){
            return false;
        }
        return true;
    }
}
