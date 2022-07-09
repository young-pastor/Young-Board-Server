package com.zhisida.board.analysis.provider;

import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.JdbcUtils;
import com.zhisida.board.analysis.enums.DimensionTypeEnum;
import com.zhisida.board.entity.BoardTable;
import com.zhisida.board.entity.BoardTableColumn;
import com.zhisida.board.enums.BoardDataSourceExceptionEnum;
import com.zhisida.board.param.*;
import com.zhisida.core.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JdbcDataSourceProvider implements DataSourceProvider<String> {
    private static final ThreadLocal<TreeMap> dataSourceConfig = new ThreadLocal<TreeMap>();
    private static Map<String, DruidDataSource> dataSourceMap = new HashMap<>();
    private static Map<Class, BeanListHandler> beanListHandlerMap = new HashMap<>();

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
        PlainSelect plainSelect = new PlainSelect();
        List<SelectItem> selectItems = new ArrayList<>();
        Table mainTable = new Table();
        EqualsTo equalsTo = new EqualsTo();
        GroupByElement groupByElement = new GroupByElement();
        List<OrderByElement> orderByElements = new ArrayList<>();

        Map<Long, Table> tableList = new HashMap<>();
        List<BoardTableConnectParam> tableConnectList = analysisParam.getTableConnectList();
        BoardTableParam tableParam = tableConnectList.get(0).getTable();
        mainTable.setName(tableParam.getTableName());
        mainTable.setAlias(new Alias(tableParam.getAliasName()));
        tableList.put(tableParam.getId(), mainTable);
        if (tableConnectList.size() > 1) {
            List<Join> joinList = tableConnectList.stream().skip(1).map(e -> {
                BoardTableParam leftTableParam = e.getTable();
                Table lTable = tableList.get(leftTableParam.getId());
                if (lTable == null) {
                    lTable = new Table();
                    lTable.setName(leftTableParam.getTableName());
                    lTable.setAlias(new Alias(leftTableParam.getAliasName()));
                    tableList.put(leftTableParam.getId(), lTable);
                }
                BoardTableParam rightTableParam = e.getConnectTable();
                Table rTable = tableList.get(rightTableParam.getId());
                if (rTable == null) {
                    rTable = new Table();
                    rTable.setName(rightTableParam.getTableName());
                    rTable.setAlias(new Alias(rightTableParam.getAliasName()));
                    tableList.put(rightTableParam.getId(), rTable);
                }

                Join join = new Join();
                join.setRightItem(rTable);
                EqualsTo condition = new EqualsTo();
                condition.setLeftExpression(new Column(lTable, e.getColumn().getColumnName()));
                condition.setRightExpression(new Column(rTable, e.getConnectColumn().getColumnName()));
                join.setOnExpression(condition);
                return join;
            }).collect(Collectors.toList());
            plainSelect.setJoins(joinList);
        }

        //条件
        if (!CollectionUtils.isEmpty(analysisParam.getEventList())) {
            analysisParam.getEventList().forEach(e -> {
                BoardEventParam eventParam = e.getEvent();
                equalsTo.setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                StringValue stringValue = new StringValue(eventParam.getValue());
                equalsTo.setRightExpression(stringValue);
            });
        }
        if (!CollectionUtils.isEmpty(analysisParam.getFilterList())) {
            analysisParam.getFilterList().forEach(e -> {
                BoardPropertyParam propertyParam = e.getProperty();
                equalsTo.setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                StringValue stringValue = new StringValue(e.getValue());
                equalsTo.setRightExpression(stringValue);
            });
        }
        //item
        if (!CollectionUtils.isEmpty(analysisParam.getPropertyList())) {
            analysisParam.getPropertyList().forEach(e -> {
                BoardPropertyParam boardPropertyParam = e.getProperty();
                SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
                selectExpressionItem.setExpression(new Column(tableList.get(boardPropertyParam.getTableId()), boardPropertyParam.getColumn().getColumnName()));
                selectExpressionItem.setAlias(new Alias(e.getAliasName()));
                selectItems.add(selectExpressionItem);
            });
        }
        if (!CollectionUtils.isEmpty(analysisParam.getDimensionList())) {
            analysisParam.getDimensionList().forEach(e -> {
                BoardPropertyParam boardPropertyParam = e.getProperty();
                String columnName = boardPropertyParam.getColumn().getColumnName();
                String expression = columnName;
                Table curTable = tableList.get(boardPropertyParam.getTableId());
                //分组
                groupByElement.setGroupByExpressions(Arrays.asList(new Column(curTable, expression)));

                //item
                SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
                selectExpressionItem.setExpression(new Column(curTable, expression));
                selectExpressionItem.setAlias(new Alias(e.getAliasName()));
                selectItems.add(selectExpressionItem);

                //order
                if (DimensionTypeEnum.ROW.name().equals(e.getType())) {
                    OrderByElement orderByElement = new OrderByElement();
                    orderByElement.setAsc(true);
                    orderByElement.setExpression(new Column(curTable, columnName));
                    orderByElements.add(orderByElement);
                }
            });
        }

        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(mainTable);
        plainSelect.setWhere(equalsTo);
        plainSelect.setGroupByElement(groupByElement);
        plainSelect.setOrderByElements(orderByElements);

        return plainSelect.toString();
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
        BeanListHandler blHandler = beanListHandlerMap.get(clazz);
        if (Objects.isNull(blHandler)) {
            blHandler = new BeanListHandler<T>(clazz, new BasicRowProcessor(new GenerousBeanProcessor()));
            beanListHandlerMap.put(clazz, blHandler);
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

}
