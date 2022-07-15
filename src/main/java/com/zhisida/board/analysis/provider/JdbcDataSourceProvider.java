package com.zhisida.board.analysis.provider;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.JdbcUtils;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.enums.BoardDataSourceExceptionEnum;
import com.zhisida.board.param.BoardAnalysisParam;
import com.zhisida.core.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.*;

@Log4j2
@Component
public class JdbcDataSourceProvider implements DataSourceProvider<String> {
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
            throw new ServiceException(BoardDataSourceExceptionEnum.GET_CONN_ERR);
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

    @Override
    public String convertAnalysisToSql(BoardAnalysisParam analysisParam) {
        return JdbcOriginQueryUtil.convertAnalysisToSql(analysisParam, JdbcUtils.getDbTypeRaw((String) getConfig().get("url"), null));
    }

    @Override
    public List<Map> queryByAnalysisParam(BoardAnalysisParam analysisParam) {
        return this.queryByOriginQuery(this.convertAnalysisToSql(analysisParam));
    }

    @Override
    public <T> List<T> queryByAnalysisParam(BoardAnalysisParam analysisParam, Class<T> clazz) {
        return this.queryByOriginQuery(this.convertAnalysisToSql(analysisParam), clazz);

    }


    public TreeMap getConfig() {
        return dataSourceConfig.get();
    }

    @Override
    public <T> List<T> queryByOriginQuery(String queryStr, Class<T> clazz) {
        ResultSetHandler blHandler;
        if (clazz == Map.class) {
            blHandler = new MapListHandler();
        } else {
            blHandler = new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor()));
        }
        Assert.isInstanceOf(String.class, queryStr, "原始查询语句不对");
        log.info("查询Sql语句：{}", queryStr);
        DruidPooledConnection connection = getConnection();
        try {
            QueryRunner qr = new QueryRunner();

            return (List<T>) qr.query(connection, queryStr, blHandler);
        } catch (Exception e) {
            log.error("使用Sql查询失败！", e);
            throw new ServiceException(BoardDataSourceExceptionEnum.QUERY_DATA_ERR);
        } finally {
            if (!Objects.isNull(connection)) {
                try {
                    connection.recycle();
                } catch (Exception e) {
                    log.error("连接回收失败！", e);
                }
            }
        }
    }

    @Override
    public List<Map> queryByOriginQuery(String queryStr) {
        return this.queryByOriginQuery(queryStr, Map.class);
    }

    @Override
    public List<BoardTable> queryTables() {
        String queryTablesSql = null;
        DbType dbType = JdbcUtils.getDbTypeRaw((String) getConfig().get("url"), null);
        switch (dbType) {
            case mysql:
                queryTablesSql = "SELECT `TABLE_NAME`, `TABLE_COMMENT` DISPLAY_NAME FROM `information_schema`.`tables` WHERE `table_schema` = database() AND `TABLE_COMMENT` IS NOT NULL";
                break;
        }
        List<BoardTable> tables = this.queryByOriginQuery(queryTablesSql, BoardTable.class);
        return tables;
    }

    @Override
    public List<BoardTableColumn> queryColumns(String table) {
        String queryColumnsSql = null;
        DbType dbType = JdbcUtils.getDbTypeRaw((String) getConfig().get("url"), null);
        switch (dbType) {
            case mysql:
                queryColumnsSql = "SELECT `COLUMN_NAME`,`COLUMN_COMMENT` DISPLAY_NAME, DATA_TYPE  FROM `information_schema`.`columns` WHERE `table_schema` = DATABASE() AND TABLE_NAME = '" + table + "'";
                break;
        }
        List<BoardTableColumn> tableColumns = this.queryByOriginQuery(queryColumnsSql, BoardTableColumn.class);
        return tableColumns;
    }

    @Override
    public List<Map> queryColumnValues(String table, String column) {
        return this.queryByOriginQuery(StrFormatter.format("SELECT DISTINCT {} FROM {}", column, table));
    }

}
