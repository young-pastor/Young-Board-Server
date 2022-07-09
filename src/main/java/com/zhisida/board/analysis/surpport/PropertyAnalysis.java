package com.zhisida.board.analysis.surpport;

import com.zhisida.board.analysis.Analysis;
import com.zhisida.board.analysis.BoardAnalysisData;
import com.zhisida.board.param.BoardAnalysisParam;
import com.zhisida.board.service.BoardDataSourceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PropertyAnalysis implements Analysis {
    @Resource
    BoardDataSourceService boardDataSourceService;


    @Override
    public void check(BoardAnalysisParam boardAnalysisParam) {
    }

    public BoardAnalysisData analysis(BoardAnalysisParam boardAnalysisParam) {
        return null;
    }


}
