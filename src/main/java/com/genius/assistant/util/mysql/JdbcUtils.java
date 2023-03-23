package com.genius.assistant.util.mysql;

import com.genius.assistant.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Genius
 * @date 2023/03/22 23:03
 **/
@Component
public class JdbcUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getDataSource() {
        return jdbcTemplate;
    }

    //获取数据库名
    public String getDatabaseName() {
        return jdbcTemplate.queryForObject("select database()", String.class);
    }

    //获取所有表名
    public List<String> getAllTableNames() {
        return jdbcTemplate.queryForList("show tables", String.class);
    }

    //获取某表的建表语句
    public String getCreateTableSql(String tableName) {
        if(StringUtils.isEmpty(tableName)){
            return "";
        }
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("show create table " + tableName);
        if(stringObjectMap.isEmpty()){
            return "";
        }
        return stringObjectMap.get("Create Table").toString();
    }

    //获得某表的注释
    public String getTableComment(String tableName) {
        String createTableSql = getCreateTableSql(tableName);
        if(StringUtils.isEmpty(createTableSql)){
            return "";
        }
        int index = createTableSql.indexOf("COMMENT='");
        if(index == -1){
            return "";
        }
        String substring = createTableSql.substring(index + 9);
        substring = substring.substring(0, substring.length()-1);
        return substring;
    }

    //获取表中所有字段的信息
    public List<Map<String,Object>> getAllColumns(String tableName){
        if(StringUtils.isEmpty(tableName)){
            return null;
        }
        String tableSql = "show full columns from " + tableName;
        return jdbcTemplate.queryForList(tableSql);
    }

    //获取某表字段的注释
    public List<String> getColumnComments(String tableName){
        List<Map<String, Object>> maps = getAllColumns(tableName);
        if(maps.isEmpty()){
            return null;
        }
        return  maps.stream().map(map -> map.get("Comment").toString()).collect(Collectors.toList());
    }

    //获取某表字段的元数据
    public ResultSetMetaData getResultSetMetaData(String tableName){
        if(StringUtils.isEmpty(tableName)){
            return null;
        }
        String sql = "select * from " + tableName + " where 1=2";
        return jdbcTemplate.query(sql, ResultSet::getMetaData);
    }
}
