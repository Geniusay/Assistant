package io.github.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.mapper.MPJJoinMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import io.github.common.JoinSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssistantMJPServiceImpl<M extends MPJJoinMapper<T>,T> extends AssistantServiceImpl<M,T> implements AssistantMJPService<T>{

    @Override
    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params, Map<String,Object> conditions, List<String> orders,Class<V> vClass,Boolean isAsc) {
        MPJQueryWrapper<V> queryWrapper = new MPJQueryWrapper<V>()
                .select(params.toArray(new String[0]))
                .allEq(conditions);
        if(orders != null && orders.size() > 0){
            if(isAsc){
                orders.forEach(
                        queryWrapper::orderByAsc
                );
            }else{
                orders.forEach(
                        queryWrapper::orderByDesc
                );
            }
        }
        return mapper.selectJoinPage(new Page<>(page,limit),vClass,queryWrapper);
    }


    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params,Class<V> vClass) {
        return BeanPageVOList(page, limit, params, Map.of(), List.of(), vClass,false);
    }

    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params,List<String> orders,Boolean isAsc,Class<V> vClass) {
        return BeanPageVOList(page, limit, params, Map.of(), orders, vClass,isAsc);
    }

    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params,Map<String,Object> conditions,Class<V> vClass) {
        return BeanPageVOList(page, limit, params, conditions, List.of(), vClass, false);
    }
    @Override
    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params, Map<String,Object> conditions, List<JoinSection> joinSections , List<String> orders, Class<V> vClass, Boolean isAsc){
        MPJQueryWrapper<V> queryWrapper = new MPJQueryWrapper<V>();

        if(!conditions.isEmpty()){
            queryWrapper.allEq(conditions);
        }

        join(queryWrapper,joinSections,params);

        if(orders != null && orders.size() > 0){
            if(isAsc){
                orders.forEach(
                        queryWrapper::orderByAsc
                );
            }else{
                orders.forEach(
                        queryWrapper::orderByDesc
                );
            }
        }
        return mapper.selectJoinPage(new Page<>(page,limit),vClass,queryWrapper);
    }

    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params, List<JoinSection> joinSections , List<String> orders, Class<V> vClass, Boolean isAsc){
        return BeanPageVOList(page,limit,params,Map.of(),joinSections,orders,vClass,isAsc);
    }

    public <V> IPage<V> BeanPageVOList(int page, int limit, List<String> params, List<JoinSection> joinSections , Class<V> vClass){
        return BeanPageVOList(page,limit,params,Map.of(),joinSections,List.of(),vClass,false);
    }

    @Override
    public <V> List<V> getBeanVOList(List<String> params,Map<String,Object> condition, Class<V> vClass) {
        MPJQueryWrapper<V> queryWrapper = new MPJQueryWrapper<V>()
                .select(params.toArray(new String[0]))
                .allEq(condition);
        return mapper.selectJoinList(vClass,queryWrapper);
    }

    @Override
    public <V> V getBeanVO(List<String> params, Map<String, Object> condition,Class<V> vClass) {
        MPJQueryWrapper<V> queryWrapper = new MPJQueryWrapper<V>().select(params.toArray(new String[0]))
                .allEq(condition);
        return mapper.selectJoinOne(vClass,queryWrapper);
    }

    @Override
    public <V> V getBeanVO(List<String> params, Map<String, Object> condition,List<JoinSection> joinSections ,Class<V> vClass){
        MPJQueryWrapper<V> queryWrapper = new MPJQueryWrapper<V>().select(params.toArray(new String[0]))
                .allEq(condition);
        join(queryWrapper,joinSections,params);
        return mapper.selectJoinOne(vClass,queryWrapper);
    }

    private <V> void join(MPJQueryWrapper<V> queryWrapper,List<JoinSection> joinSections,List<String> params){
        List<String> newParams = new ArrayList<>(params);
        joinSections.forEach(
                section->{
                    newParams.add(section.getSelectSQL());
                    String sql = String.format("%s %s on %s",
                            section.getTable(),
                            section.getAsName(),
                            section.getConnectColumn());
                    switch (section.getType()){
                        case LEFT: queryWrapper.leftJoin(sql);break;
                        case RIGHT: queryWrapper.rightJoin(sql);break;
                        case INNER: queryWrapper.innerJoin(sql);break;
                        default:break;
                    }
                }
        );
        queryWrapper.select(newParams.toArray(new String[0]));
    }
}
