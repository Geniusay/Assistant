package io.github.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.common.web.PageVO;

import java.util.ArrayList;

public class PageUtil <T>{

    public PageVO<T> toPageVO(IPage<T> page){
        return new PageVO<T>(
                page.getRecords(),
                page.getCurrent(),
                page.getSize(),
                page.getTotal()
        );
    }

    public PageVO<T> emptyPageVO(){
        return new PageVO<T>(new ArrayList<T>(), -1, 0, 0);
    }

}
