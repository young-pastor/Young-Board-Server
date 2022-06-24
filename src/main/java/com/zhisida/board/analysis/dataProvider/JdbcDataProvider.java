package com.zhisida.board.analysis.dataProvider;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Component("jdbcDataProvider")
public class JdbcDataProvider extends AbstractDataProvider {
    private static Map<String, DruidDataSource> dataSourceMap = new HashMap<>();

    private DruidPooledConnection getConnection() {
        String sourceKey = JSONUtil.toJsonStr(getDataSource());
        DruidDataSource druidDataSource = dataSourceMap.get(sourceKey);
        if (Objects.isNull(druidDataSource)) {
            try {
                druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(getDataSource());
                dataSourceMap.put(sourceKey, druidDataSource);
            } catch (Exception e) {
            }
        }
        try {
            return druidDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> queryAggData(SQLSelectStatement sqlSelectStatement) {
        return queryBuySql(sqlSelectStatement.toString());
    }

    @Override
    public List<Map<String, Object>> queryBuySql(String sql) {
        DruidPooledConnection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> resList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> resRow = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    resRow.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                resList.add(resRow);
            }
            return resList;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public List<Map<String, Object>> queryTables() {
        String queryTablesSql = null;
        DbType dbType = JdbcUtils.getDbTypeRaw((String) getDataSource().get("url"), null);
        switch (dbType) {
            case mysql:
                queryTablesSql = "SELECT `TABLE_NAME`, `TABLE_COMMENT` DISPLAY_NAME FROM `information_schema`.`tables` WHERE `table_schema` = database() AND `TABLE_COMMENT` IS NOT NULL";
                break;
            case oracle:
                queryTablesSql = "select * from user_tables;";
                break;
            case presto:
                queryTablesSql = "select * from user_tables;";
                break;
            case clickhouse:
                queryTablesSql = "SELECT `NAME` TABLE_NAME, '' DISPLAY_NAME FROM `system`.`tables` ";
                break;
        }
        return queryBuySql(queryTablesSql);
    }

    @Override
    public List<Map<String, Object>> queryColumns(String table) {
        String queryColumnsSql = null;
        return queryBuySql(queryColumnsSql);
    }

    private void close(DruidPooledConnection connection, Statement statement, ResultSet resultSet) {
        if (!Objects.isNull(connection)) {
            try {
                connection.recycle();
            } catch (Exception e) {

            }
        }
        if (!Objects.isNull(statement)) {
            try {
                statement.close();
            } catch (Exception e) {

            }
        }
        if (!Objects.isNull(resultSet)) {
            try {
                resultSet.close();
            } catch (Exception e) {

            }
        }
    }
}
