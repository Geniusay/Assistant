package io.github.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinSection {
    public enum JoinType{
        LEFT,
        RIGHT,
        INNER,
        JOIN
    }

    //插入类型
    private JoinType type;

    //插入表
    private String table;

    //插入表 as name
    private String asName;

    //连接条件
    private String connectColumn;
}
