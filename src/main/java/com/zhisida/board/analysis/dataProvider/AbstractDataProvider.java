package com.zhisida.board.analysis.dataProvider;

import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

import java.util.*;

public abstract class AbstractDataProvider {
    private TreeMap dataSource;

    public Map getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map dataSource) {
        this.dataSource = new TreeMap(dataSource);
    }

    public abstract List<Map<String, Object>> queryAggData(SQLSelectStatement sqlSelectStatement);

    public abstract List<Map<String, Object>> queryBuySql(String sql);

    public abstract List<Map<String, Object>> queryTables();

    public abstract List<Map<String, Object>> queryColumns(String table);
}
