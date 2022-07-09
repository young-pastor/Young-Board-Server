package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.zhisida.board.entity.BoardEvent;
import com.zhisida.board.param.BoardEventParam;
import com.zhisida.board.service.BoardEventService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BoardEventCache {
    @Resource
    private BoardEventService boardEventService;

    //@Cacheable(cacheNames = "Young:Board:Event:Detail:Param", key = "#id", unless = " #result == null")
    public BoardEventParam getEventParamById(Long id) {
        BoardEvent boardEvent = boardEventService.getById(id);
        BoardEventParam boardEventParam = new BoardEventParam();
        BeanUtil.copyProperties(boardEvent, boardEventParam, true);
        return boardEventParam;
    }
}
