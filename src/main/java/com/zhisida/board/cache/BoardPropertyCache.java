package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.zhisida.board.entity.BoardProperty;
import com.zhisida.board.param.BoardPropertyParam;
import com.zhisida.board.service.BoardPropertyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BoardPropertyCache {
    @Resource
    private BoardPropertyService boardPropertyService;

    //@Cacheable(cacheNames = "Young:Board:Property:Detail:Param", key = "#id", unless = " #result == null")
    public BoardPropertyParam getPropertyParamById(Long id) {
        BoardProperty boardProperty = boardPropertyService.getById(id);
        BoardPropertyParam boardPropertyParam = new BoardPropertyParam();
        BeanUtil.copyProperties(boardProperty, boardPropertyParam, true);
        return boardPropertyParam;
    }
}
