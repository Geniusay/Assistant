package io.github.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import java.util.List;
import java.util.Map;

public interface AssistantService<T> {

    IPage<T> BeanPageList(int page, int limit, List<String> params);
    IPage<T> BeanPageList(int page, int limit, List<String> params , List<String> orders, Boolean isAsc);

    T getBean(List<String> params, Map<String,Object> condition);

    List<T> getBeanList(List<String> params,Map<String, Object> condition);
    List<T> batchBeanList(List<String> params, Map<String,List<Object>> inCondition);

    Boolean updateBean(T t, Map<String,Object> condition);

    Boolean deleteBean( Map<String,Object> condition);
}
