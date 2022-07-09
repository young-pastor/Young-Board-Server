package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhisida.board.entity.BoardAnalysisDimension;
import com.zhisida.board.param.BoardAnalysisDimensionParam;
import com.zhisida.board.service.BoardAnalysisDimensionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardAnalysisDimensionCache {
    @Resource
    BoardAnalysisDimensionService boardAnalysisDimensionService;

    //@Cacheable(cacheNames = "Young:Board:AnalysisDimension:ListByAnalysisId", key = "#analysisId", unless = " #result == null")
    public List<BoardAnalysisDimensionParam> getDimensionListByAnalysisId(Long analysisId) {
        QueryWrapper<BoardAnalysisDimension> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BoardAnalysisDimension::getAnalysisId,analysisId);
        List<BoardAnalysisDimension> boardAnalysisDimensionList = boardAnalysisDimensionService.list(queryWrapper);
        List<BoardAnalysisDimensionParam> boardAnalysisDimensionParamList = new ArrayList<>();
        boardAnalysisDimensionList.forEach(e->{
            BoardAnalysisDimensionParam boardAnalysisDimensionParam = new BoardAnalysisDimensionParam();
            BeanUtil.copyProperties(e, boardAnalysisDimensionParam, true);
            boardAnalysisDimensionParamList.add(boardAnalysisDimensionParam);
        });
        return boardAnalysisDimensionParamList;
    }
}
