package io.github.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

public interface AssistantMJPService<T> extends AssistantService<T> {

    <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params ,Map<String,Object> conditions, List<String> orders,Class<V> vClass,Boolean isAes);


    <V> List<V> getBeanVOList(List<String> params,Map<String,Object> condition,Class<V> vClass);

    <V> V getBeanVO(List<String> params, Map<String,Object> condition, Class<V> vClass);
}
