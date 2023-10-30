package io.github.common.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *    Genius
 **/

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class DataBase {
    private String dataBaseName;    //数据库名
    private List<Table> tables;     //数据库表信息
}
