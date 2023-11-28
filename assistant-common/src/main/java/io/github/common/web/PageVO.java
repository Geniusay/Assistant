package io.github.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据库分页展示类
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    private List<T> pageList;

    private long page;

    private long limit;

    private long total;
}
