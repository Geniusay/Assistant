package com.genius.assistant.util;

import com.genius.assistant.util.mysql.JdbcUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Genius
 * @date 2023/03/22 23:13
 **/

@SpringBootTest
public class JdbcUtilTest {


    Logger logger = LoggerFactory.getLogger(JdbcUtilTest.class);

    @Autowired
    JdbcUtils jdbcUtils;


    /**
     * 测试获取所有表名
     */
    @Test
    public void TestGetAllTableNames() {
        logger.info(jdbcUtils.getAllTableNames().toString());
    }

    /**
     * 测试获取某表的建表语句
     */
    @Test
    public void TestGetCreateTableSql() {
        logger.info(jdbcUtils.getCreateTableSql("user"));
    }


    /**
     * 测试获取某表的注释
     */
    @Test
    public void TestGetTableComment() {
        logger.info(jdbcUtils.getTableComment("user").toString());
    }

    /**
     * 测试获取某表字段的注释
     */
    @Test
    public void TestGetColumnComments() {
        logger.info(jdbcUtils.getColumnComments("user").toString());
    }

    /**
     * 测试获取某表字段的类型
     */
    @Test
    public void TestGetColumnTypes() throws SQLException {
        ResultSetMetaData user = jdbcUtils.getResultSetMetaData("user");
        for (int i = 0; i < user.getColumnCount(); i++) {
            System.out.print(" java类型:"+user.getColumnClassName(i+1));
            System.out.print(" 数据库类型:"+user.getColumnTypeName(i+1));
            System.out.print(" 字段名:"+user.getColumnName(i+1));
            System.out.print(" 字段注释:"+user.getColumnLabel(i+1));
            System.out.print(" 字段长度:"+user.getColumnDisplaySize(i+1));
            System.out.println("字段是否为空:"+(user.isNullable(i+1)==1?"是":"否"));
        }
    }

}
