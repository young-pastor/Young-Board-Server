package com.zhisida.board.cache;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhisida.board.entity.BoardAnalysisEvent;
import com.zhisida.board.param.BoardAnalysisEventParam;
import com.zhisida.board.service.BoardAnalysisEventService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardAnalysisEventCache {
    @Resource
    BoardAnalysisEventService boardAnalysisEventService;

    //@Cacheable(cacheNames = "Young:Board:AnalysisEvent:ListByAnalysisId", key = "#analysisId", unless = " #result == null")
    public List<BoardAnalysisEventParam> getEventListByAnalysisId(Long analysisId) {
        QueryWrapper<BoardAnalysisEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BoardAnalysisEvent::getAnalysisId, analysisId);
        List<BoardAnalysisEvent> boardAnalysisEventList = boardAnalysisEventService.list(queryWrapper);
        List<BoardAnalysisEventParam> boardAnalysisEventParamList = new ArrayList<>();
        boardAnalysisEventList.forEach(e -> {
            BoardAnalysisEventParam boardAnalysisEventParam = new BoardAnalysisEventParam();
            BeanUtil.copyProperties(e, boardAnalysisEventParam, true);
            boardAnalysisEventParamList.add(boardAnalysisEventParam);
        });
        return boardAnalysisEventParamList;
    }
}
