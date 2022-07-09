package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhisida.board.entity.BoardAnalysisProperty;
import com.zhisida.board.param.BoardAnalysisPropertyParam;
import com.zhisida.board.service.BoardAnalysisPropertyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardAnalysisPropertyCache {
    @Resource
    BoardAnalysisPropertyService boardAnalysisPropertyService;

    //@Cacheable(cacheNames = "Young:Board:AnalysisProperty:ListByAnalysisId", key = "#analysisId", unless = " #result == null")
    public List<BoardAnalysisPropertyParam> getPropertyListByAnalysisId(Long analysisId) {
        QueryWrapper<BoardAnalysisProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BoardAnalysisProperty::getAnalysisId,analysisId);
        List<BoardAnalysisProperty> boardAnalysisPropertyList = boardAnalysisPropertyService.list(queryWrapper);
        List<BoardAnalysisPropertyParam> boardAnalysisPropertyParamList = new ArrayList<>();
        boardAnalysisPropertyList.forEach(e->{
            BoardAnalysisPropertyParam boardAnalysisPropertyParam = new BoardAnalysisPropertyParam();
            BeanUtil.copyProperties(e, boardAnalysisPropertyParam, true);
            boardAnalysisPropertyParamList.add(boardAnalysisPropertyParam);
        });
        return boardAnalysisPropertyParamList;
    }
}
