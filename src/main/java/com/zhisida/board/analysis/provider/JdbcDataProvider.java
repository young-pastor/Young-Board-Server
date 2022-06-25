package com.zhisida.board.analysis.provider;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.JdbcUtils;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.enums.BoardDataSourceExceptionEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component("jdbcDataProvider")
public class JdbcDataProvider implements DataProvider {
    private static final ThreadLocal<TreeMap> dataSourceConfig = new ThreadLocal<TreeMap>();

    private static Map<String, DruidDataSource> dataSourceMap = new HashMap<>();

    private DruidPooledConnection getConnection() {
        String sourceKey = JSONUtil.toJsonStr(getConfig());
        DruidDataSource druidDataSource = dataSourceMap.get(sourceKey);
        if (Objects.isNull(druidDataSource)) {
            try {
                druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(getConfig());
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
    public void setConfig(Map dataSource) {
        dataSourceConfig.set(new TreeMap(dataSource));
    }

    @Override
    public void setConfig(String dataSource) {
        setConfig(JSONUtil.toBean(dataSource, Map.class));
    }

    public TreeMap getConfig() {
        return dataSourceConfig.get();
    }

    @Override
    public List<Map<String, Object>> queryBuyOriginQuery(String queryStr) {
        log.info("查询Sql语句：{}", queryStr);
        DruidPooledConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(queryStr);
            resultSet = statement.executeQuery();
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
            log.error("使用Sql查询失败！",e);
            throw new ServiceException(BoardDataSourceExceptionEnum.QUERY_DATA_ERR);
        } finally {
            close(connection, statement, resultSet);
        }
    }

    @Override
    public List<BoardTable> queryTables() {
        String queryTablesSql = null;
        DbType dbType = JdbcUtils.getDbTypeRaw((String) getConfig().get("url"), null);
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
        List<Map<String, Object>> data = queryBuyOriginQuery(queryTablesSql);
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().map(item -> BeanUtil.toBeanIgnoreCase(item, BoardTable.class, true)).collect(Collectors.toList());
    }

    @Override
    public List<BoardTableColumn> queryColumns(String table) {
        String queryColumnsSql = null;
        DbType dbType = JdbcUtils.getDbTypeRaw((String) getConfig().get("url"), null);
        switch (dbType) {
            case mysql:
                queryColumnsSql = "SELECT `COLUMN_NAME`,`COLUMN_COMMENT` DISPLAY_NAME,DATA_TYPE  FROM `information_schema`.`columns` WHERE `table_schema` = DATABASE() AND TABLE_NAME = '" + table + "'";
                break;
        }
        List<Map<String, Object>> data = queryBuyOriginQuery(queryColumnsSql);
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().map(item -> BeanUtil.toBeanIgnoreCase(item, BoardTableColumn.class, true)).collect(Collectors.toList());

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
