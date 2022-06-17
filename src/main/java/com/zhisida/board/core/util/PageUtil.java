
package com.zhisida.board.core.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 分页工具类针对hutool分页的扩展
 *
 * @author young-pastor
 **/
public class PageUtil<T> extends cn.hutool.core.util.PageUtil{

    /**
     * 逻辑分页
     *
     * @author young-pastor
     **/
    public static <T> List<T> page(Page<T> page, List<T> list) {
        setFirstPageNo(1);
        int start = getStart(Convert.toInt(page.getCurrent()), Convert.toInt(page.getSize()));
        int end = getEnd(Convert.toInt(page.getCurrent()), Convert.toInt(page.getSize()));
        if(start > list.size()) {
            return CollectionUtil.newArrayList();
        }else if(start > end) {
            return CollectionUtil.newArrayList();
        } else if(end > list.size()) {
            return list.subList(start, list.size());
        } else {
            return list.subList(start, end);
        }
    }
}
