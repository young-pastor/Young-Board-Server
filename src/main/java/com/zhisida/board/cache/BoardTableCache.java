package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.param.BoardTableParam;
import com.zhisida.board.service.BoardTableService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BoardTableCache {
    @Resource
    private BoardTableService boardTableService;

    //@Cacheable(cacheNames = "Young:Board:Table:Detail:Param", key = "#id", unless = " #result == null")
    public BoardTableParam getTableParamById(Long id) {
        BoardTable boardTable = boardTableService.getById(id);
        BoardTableParam boardTableParam = new BoardTableParam();
        BeanUtil.copyProperties(boardTable, boardTableParam, true);
        return boardTableParam;
    }
}
