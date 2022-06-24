package com.zhisida.board.analysis.provider;

import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;

import java.util.*;

public  interface DataProvider {
    public void setConfig(Map dataSource) ;
    public void setConfig(String dataSource) ;
    public abstract List<Map<String, Object>> queryAggData(SQLSelectStatement sqlSelectStatement);

    public abstract List<Map<String, Object>> queryBuySql(String sql);

    public abstract List<BoardTable> queryTables();

    public abstract List<BoardTableColumn> queryColumns(String table);
}
