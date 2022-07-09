package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhisida.board.entity.BoardAnalysisFilter;
import com.zhisida.board.param.BoardAnalysisFilterParam;
import com.zhisida.board.service.BoardAnalysisFilterService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardAnalysisFilterCache {
    @Resource
    BoardAnalysisFilterService boardAnalysisFilterService;

    //@Cacheable(cacheNames = "Young:Board:AnalysisFilter:ListByAnalysisId", key = "#analysisId", unless = " #result == null")
    public List<BoardAnalysisFilterParam> getFilterListByAnalysisId(Long analysisId) {
        QueryWrapper<BoardAnalysisFilter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BoardAnalysisFilter::getAnalysisId,analysisId);
        List<BoardAnalysisFilter> boardAnalysisFilterList = boardAnalysisFilterService.list(queryWrapper);
        List<BoardAnalysisFilterParam> boardAnalysisFilterParamList = new ArrayList<>();
        boardAnalysisFilterList.forEach(e->{
            BoardAnalysisFilterParam boardAnalysisFilterParam = new BoardAnalysisFilterParam();
            BeanUtil.copyProperties(e, boardAnalysisFilterParam, true);
            boardAnalysisFilterParamList.add(boardAnalysisFilterParam);
        });
        return boardAnalysisFilterParamList;
    }
}
