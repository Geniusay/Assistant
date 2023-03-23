package com.genius.assistant.common.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Genius
 * @date 2023/03/23 00:24
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    private Class type;             //字段类型
    private String typeInSql;       //字段类型
    private String name;            //字段名
    private String comment;         //字段注释
    private Object defaultValue;    //默认值
    private Integer length;         //字段长度
    private Boolean isNull;         //是否为空
    private Boolean isPrimaryKey;   //是否为主键
}
