package com.zhisida.board.analysis.provider;

import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.param.BoardAnalysisParam;

import java.util.List;
import java.util.Map;

public interface DataSourceProvider<Q> {

    public void setConfig(Map dataSource);

    public void setConfig(String dataSource);

    public String convertAnalysisToSql(BoardAnalysisParam analysisParam);

    public List<Map> queryByAnalysisParam(BoardAnalysisParam analysisParam);

    public <T> List<T> queryByAnalysisParam(BoardAnalysisParam analysisParam, Class<T> clazz);

    public <T> List<T> queryByOriginQuery(Q queryStr, Class<T> clazz);

    public List<Map> queryByOriginQuery(Q queryStr);

    public List<BoardTable> queryTables();

    public List<BoardTableColumn> queryColumns(String table);
}
