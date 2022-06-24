package com.zhisida.board;

import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;

import javax.sql.DataSource;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDruid {
    public static void main(String args[]) throws Exception {
//        driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://localhost:3306/young-board?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT&nullCatalogMeansCurrent=true
//        username: root
//        password: 123456
        Map map = new HashMap();
        map.put("url", "jdbc:mysql://localhost:3306/young-board?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT&nullCatalogMeansCurrent=true");
        map.put("username", "root");
        map.put("password", "123456");
        DruidDataSource dataSource1 = new DruidDataSource();

        DataSource dataSource = DruidDataSourceFactory.createDataSource(map);
        System.out.println(dataSource.getConnection().getMetaData().getURL());
//        parseSql();
//        createSql();
    }

    public static void parseSql() throws Exception {

        String sql = "select p.name from user u join role r on u.role_id = r.oid join post p on p.oid = r.post_id";
        String dbType = "mysql";
        System.out.println("原始SQL 为 ： " + SQLUtils.format(sql, dbType));
        SQLSelectStatement statement = (SQLSelectStatement) parser(sql, dbType);
        SQLSelect select = statement.getSelect();
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) select.getQuery();
        // 这里新增的条件，如果语法不正确会报错。如果条件不正确，需要执行了sql后才会报错。
        query.addCondition("name = date_format(date,'%Y-%m-%d')");
        System.out.println("修改SQL 为 ： " + statement.toString());
    }

    public static SQLStatement parser(String sql, String dbType) throws SQLSyntaxErrorException {
        List<SQLStatement> list = SQLUtils.parseStatements(sql, dbType);
        if (list.size() > 1) {
            throw new SQLSyntaxErrorException("MultiQueries is not supported,use single query instead ");
        }
        return list.get(0);
    }

    public static void createSql() {
        //解析select查询
        SQLSelectStatement statement = new SQLSelectStatement(DbType.clickhouse);
        SQLSelect sqlSelect = new SQLSelect();
        statement.setSelect(sqlSelect);
        SQLSelectQueryBlock query = new SQLSelectQueryBlock();
        sqlSelect.setQuery(query);
        query.addCondition("name = date_format(date,'%Y-%m-%d')");
        query.addSelectItem("p.name", "m");

        SQLJoinTableSource tableSource = new SQLJoinTableSource();
        tableSource.setJoinType(SQLJoinTableSource.JoinType.JOIN);
        SQLJoinTableSource sqlJoinTableSource = new SQLJoinTableSource();
        sqlJoinTableSource.setJoinType(SQLJoinTableSource.JoinType.JOIN);
        sqlJoinTableSource.setLeft("TAL_B", "b");
        sqlJoinTableSource.setRight("TAL_C", "c");
        SQLBinaryOpExpr sqlBinaryOpExpr = new SQLBinaryOpExpr();
        SQLPropertyExpr joinLeft = new SQLPropertyExpr();
        joinLeft.setOwner("a");
        joinLeft.setName("Name");
        sqlBinaryOpExpr.setLeft(joinLeft);
        SQLPropertyExpr joinRight = new SQLPropertyExpr();
        joinRight.setOwner("b");
        joinRight.setName("Name");
        sqlBinaryOpExpr.setRight(joinRight);
        sqlBinaryOpExpr.setOperator(SQLBinaryOperator.Equality);
        sqlJoinTableSource.setCondition(sqlBinaryOpExpr);


        tableSource.setLeft(sqlJoinTableSource);
        tableSource.setRight("TBL_A", "a");
        query.setFrom(tableSource);
        System.out.println("修改表名后的SQL 为 ： [" + statement.toString() + "]");
    }
}
