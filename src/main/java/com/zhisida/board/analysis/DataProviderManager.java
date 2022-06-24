package com.zhisida.board.analysis;

import com.zhisida.board.analysis.provider.DataProvider;
import com.zhisida.board.analysis.provider.JdbcDataProvider;
import com.zhisida.board.entity.BoardDataSource;


public class DataProviderManager {
    public static DataProvider getDataProviderByType(BoardDataSource boardDataSource) {
        DataProvider dataProvider = new JdbcDataProvider();
        dataProvider.setConfig(boardDataSource.getConfig());
        return dataProvider;
    }
}
