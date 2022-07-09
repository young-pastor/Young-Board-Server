package com.zhisida.board.analysis;

import com.zhisida.board.param.BoardAnalysisParam;

public interface Analysis {
    public void check(BoardAnalysisParam boardAnalysisParam);
    public BoardAnalysisData analysis(BoardAnalysisParam boardAnalysisParam);
}
