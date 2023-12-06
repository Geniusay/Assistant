package io.github.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class AssistantServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements AssistantService<T> {

    @Resource
    M mapper;

    @Override
    public IPage<T> BeanPageList(int page, int limit, List<String> params,Map<String,Object> conditions, List<String> orders, Boolean isAsc) {
        Page<T> pageQuery = new Page<>(page, limit);
        QueryWrapper<T> select = new QueryWrapper<T>().select(params.toArray(new String[0]));
        if(orders!=null && orders.size()>0){
            if (isAsc){
                orders.forEach(
                        select::orderByAsc
                );
            }else{
                orders.forEach(
                        select::orderByDesc
                );
            }
        }
        return mapper.selectPage(pageQuery,select);
    }


    public IPage<T> BeanPageList(int page, int limit, List<String> params) {
        return BeanPageList(page, limit, params,Map.of(),List.of(),false);
    }

    public IPage<T> BeanPageList(int page, int limit, List<String> params, Map<String,Object> conditions) {
        return BeanPageList(page, limit, params,conditions,List.of(),false);
    }

    public IPage<T> BeanPageList(int page, int limit, List<String> params, List<String> orders, Boolean isAsc) {
        return BeanPageList(page, limit, params,Map.of(),orders,isAsc);
    }

    @Override
    public T getBean(List<String> params, Map<String, Object> condition) {
        return query().select(params.toArray(new String[0]))
                .allEq(condition)
                .one();
    }

    public List<T> getBeanList(List<String> params,Map<String, Object> condition){
        return query().select(params.toArray(new String[0]))
                .allEq(condition)
                .list();
    }

    @Override
    public List<T> batchBeanList(List<String> params, Map<String, List<Object>> inCondition) {
        QueryChainWrapper<T> selectChain = query().select(params.toArray(new String[0]));
        inCondition.forEach(
                selectChain::in
        );
        return selectChain.list();
    }

    @Override
    public Boolean updateBean(T t, Map<String, Object> condition) {
        return mapper.update(t,new QueryWrapper<T>().allEq(condition))>0;
    }

    @Override
    public Boolean deleteBean(Map<String, Object> condition) {
        return mapper.deleteByMap(condition)>0;
    }
}
