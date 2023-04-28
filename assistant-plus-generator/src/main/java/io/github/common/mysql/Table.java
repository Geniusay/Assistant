package io.github.common.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Genius

 **/


/**
 * 表信息
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private String tableName;       //表名
    private String tableComment;    //表注释
    private String tableCreateSql;  //建表语句
    private List<Column> columns;   //表字段信息
}
