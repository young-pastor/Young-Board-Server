package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.param.BoardTableColumnParam;
import com.zhisida.board.service.BoardTableColumnService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BoardTableColumnCache {
    @Resource
    private BoardTableColumnService boardTableColumnService;

    @Cacheable(cacheNames = "Young:Board:Table:Column:Detail:Param", key = "#id", unless = " #result == null")
    public BoardTableColumnParam getTableColumnParamById(Long id) {
        BoardTableColumn boardTableColumn = boardTableColumnService.getById(id);
        BoardTableColumnParam boardTableColumnParam = new BoardTableColumnParam();
        BeanUtil.copyProperties(boardTableColumn, boardTableColumnParam, true);
        return boardTableColumnParam;
    }
    @CachePut(cacheNames = "Young:Board:Table:Column:Detail:Param", key = "#boardTableColumn.id", unless = " #result == null")
    public BoardTableColumn putTableColumnParam(BoardTableColumn boardTableColumn){
        return boardTableColumn;
    }
    @CacheEvict(cacheNames = "Young:Board:Table:Column:Detail:Param", key = "#id")
    public void clearTableColumnParam(Long id){
    }
}
