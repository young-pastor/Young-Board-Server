package com.zhisida.board.analysis.provider;

import com.zhisida.board.analysis.enums.DimensionTypeEnum;
import com.zhisida.board.analysis.enums.FilterLogicEnum;
import com.zhisida.board.analysis.enums.FilterMeasureEnum;
import com.zhisida.board.analysis.enums.IndicatorMeasureEnum;
import com.zhisida.board.param.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


public class JdbcConvertOriginQuery {

    public static String convertAnalysisToSql(BoardAnalysisParam analysisParam) {
        PlainSelect plainSelect = new PlainSelect();

        List<SelectItem> selectItems = new ArrayList<>();
        Table mainTable = new Table();
        Expression whereExp = null;
        GroupByElement groupByElement = null;
        List<OrderByElement> orderByElements = null;
        List<Join> joinList = null;

        Map<Long, Table> tableList = new HashMap<>();

        //MainTable
        List<BoardTableConnectParam> tableConnectList = analysisParam.getTableConnectList();
        BoardTableParam tableParam = tableConnectList.get(0).getTable();
        mainTable.setName(tableParam.getTableName());
        mainTable.setAlias(new Alias(tableParam.getAliasName()));
        tableList.put(tableParam.getId(), mainTable);
        if (tableConnectList.size() > 1) {
            joinList = convertTableConnectToJoin(tableList, tableConnectList);
        }
        //条件
        Expression eventExp = null;
        if (!CollectionUtils.isEmpty(analysisParam.getEventList())) {
            eventExp = convertEventToWhereExpression(tableList, analysisParam.getEventList());
        }
        Expression filterExp = null;
        if (!CollectionUtils.isEmpty(analysisParam.getFilterList())) {
            filterExp = convertFilterToWhereExpression(analysisParam.getSubLogic(), tableList, analysisParam.getFilterList());
        }
        if (eventExp != null && filterExp != null) {
            AndExpression andExpression = new AndExpression();
            andExpression.setLeftExpression(eventExp);
            andExpression.setRightExpression(filterExp);
            whereExp = andExpression;
        } else if (eventExp != null) {
            whereExp = eventExp;
        } else if (filterExp != null) {
            whereExp = filterExp;
        }
        //查询Item
        if (!CollectionUtils.isEmpty(analysisParam.getPropertyList())) {
            selectItems.addAll(convertPropertyToSelectItems(mainTable, tableList, analysisParam.getPropertyList()));
        }
        //Group Order
        if (!CollectionUtils.isEmpty(analysisParam.getDimensionList())) {
            selectItems.addAll(convertDimensionToSelectItems(tableList, analysisParam.getDimensionList()));
            groupByElement = convertDimensionToGroupByElement(tableList, analysisParam.getDimensionList());
            orderByElements = convertDimensionToOrderByElements(tableList, analysisParam.getDimensionList());
        }
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(mainTable);
        plainSelect.setJoins(joinList);
        plainSelect.setWhere(whereExp);
        plainSelect.setGroupByElement(groupByElement);
        plainSelect.setOrderByElements(orderByElements);

        return plainSelect.toString();
    }

    private static Expression convertFilterToWhereExpression(String parentLogic, Map<Long, Table> tableList, List<BoardAnalysisFilterParam> filterList) {
        Expression filterExp = null;
        for (int i = 0; i < filterList.size(); i++) {
            //TODO 需要排列条件
            BoardAnalysisFilterParam e = filterList.get(i);
            BoardPropertyParam propertyParam = e.getProperty();

            Expression condition = null;
            FilterMeasureEnum measure = FilterMeasureEnum.valueOf(e.getMeasure());
            switch (measure) {
                case IS_NULL:     //为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    break;
                case NOT_NULL:    //不为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((IsNullExpression) condition).setNot(true);
                    break;
                case IS_EMPTY:    //没值
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((EqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case NOT_EMPTY:   //有值
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((NotEqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case IS_TRUE:    //为真
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((IsBooleanExpression) condition).setIsTrue(true);
                    break;
                case IS_FALSE:   //为假
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((IsBooleanExpression) condition).setIsTrue(false);
                    break;
                case EQUAL:       //等于
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((EqualsTo) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case NOT_EQUAL:   //不等于
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((NotEqualsTo) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case LESS_THAN:         //小于
                    condition = new MinorThan();
                    ((MinorThan) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((MinorThan) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case LESS_THAN_EQUAL:   //小于等于
                    condition = new MinorThanEquals();
                    ((MinorThanEquals) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((MinorThanEquals) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case GREATER_THAN:      //大于
                    condition = new GreaterThan();
                    ((GreaterThan) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((GreaterThan) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case GREATER_THAN_EQUAL://大于等于
                    condition = new GreaterThanEquals();
                    ((GreaterThanEquals) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((GreaterThanEquals) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case RANGE:             //范围
                    condition = new Between();
                    ((Between) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((Between) condition).setBetweenExpressionStart(new StringValue(e.getValue()));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(e.getValue()));
                    break;
                case IN:                //全包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((InExpression) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case NOT_IN:            //全不包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((InExpression) condition).setRightExpression(new StringValue(e.getValue()));
                    ((InExpression) condition).setNot(true);
                    break;
                case LIKE:              //包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case LEFT_LIKE:         //左包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case RIGHT_LIKE:        //右包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case NOT_LIKE:          //不包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(e.getValue()));
                    ((LikeExpression) condition).setNot(true);
                    break;
                case MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case MATCH_IGNORE_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case NOT_MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(e.getValue()));
                    break;
                case NOT_MATCH_IGNORE_CASE:         //正则不匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(e.getValue()));
                    break;

            }

            if (i < 1) {
                filterExp = condition;
            } else {
                BinaryExpression binaryExpression = null;
                if (FilterLogicEnum.AND.name().equals(parentLogic)) {
                    binaryExpression = new AndExpression();
                } else {
                    binaryExpression = new OrExpression();
                }
                binaryExpression.setLeftExpression(filterExp);
                binaryExpression.setRightExpression(condition);
                filterExp = binaryExpression;
            }
        }
        return filterExp;
    }

    private static Expression convertEventToWhereExpression(Map<Long, Table> tableList, List<BoardAnalysisEventParam> eventList) {
        Expression eventExp = null;
        for (int i = 0; i < eventList.size(); i++) {
            BoardAnalysisEventParam e = eventList.get(i);
            BoardEventParam eventParam = e.getEvent();

            Expression condition = null;
            FilterMeasureEnum measure = FilterMeasureEnum.valueOf(eventParam.getMeasure());
            switch (measure) {
                case IS_NULL:     //为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    break;
                case NOT_NULL:    //不为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((IsNullExpression) condition).setNot(true);
                    break;
                case IS_EMPTY:    //没值
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((EqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case NOT_EMPTY:   //有值
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((NotEqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case IS_TRUE:    //为真
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((IsBooleanExpression) condition).setIsTrue(true);
                    break;
                case IS_FALSE:   //为假
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((IsBooleanExpression) condition).setIsTrue(false);
                    break;
                case EQUAL:       //等于
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((EqualsTo) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case NOT_EQUAL:   //不等于
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((NotEqualsTo) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case LESS_THAN:         //小于
                    condition = new MinorThan();
                    ((MinorThan) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((MinorThan) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case LESS_THAN_EQUAL:   //小于等于
                    condition = new MinorThanEquals();
                    ((MinorThanEquals) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((MinorThanEquals) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case GREATER_THAN:      //大于
                    condition = new GreaterThan();
                    ((GreaterThan) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((GreaterThan) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case GREATER_THAN_EQUAL://大于等于
                    condition = new GreaterThanEquals();
                    ((GreaterThanEquals) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((GreaterThanEquals) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case RANGE:             //范围
                    condition = new Between();
                    ((Between) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((Between) condition).setBetweenExpressionStart(new StringValue(eventParam.getValue()));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(eventParam.getValue()));
                    break;
                case IN:                //全包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((InExpression) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case NOT_IN:            //全不包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((InExpression) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    ((InExpression) condition).setNot(true);
                    break;
                case LIKE:              //包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case LEFT_LIKE:         //左包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case RIGHT_LIKE:        //右包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case NOT_LIKE:          //不包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((LikeExpression) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    ((LikeExpression) condition).setNot(true);
                    break;
                case MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case MATCH_IGNORE_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case NOT_MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
                case NOT_MATCH_IGNORE_CASE:         //正则不匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName()));
                    ((RegExpMatchOperator) condition).setRightExpression(new StringValue(eventParam.getValue()));
                    break;
            }
            if (i < 1 && Objects.isNull(eventExp)) {
                eventExp = condition;
            } else {
                AndExpression andExpression = new AndExpression();
                andExpression.setLeftExpression(eventExp);
                andExpression.setRightExpression(condition);
                eventExp = andExpression;
            }
        }
        return eventExp;
    }

    private static Collection<? extends SelectItem> convertPropertyToSelectItems(Table mainTable, Map<Long, Table> tableList, List<BoardAnalysisPropertyParam> propertyList) {
        return propertyList.stream().map(e -> {
            //TODO 目前不支持 USER,COUNT_USER_AVG,SUN_USER_AVG
            BoardPropertyParam boardPropertyParam = e.getProperty();
            String columnName = "*";
            Table indicatorTable = mainTable;
            if (!Objects.isNull(boardPropertyParam)) {
                if (!Objects.isNull(boardPropertyParam.getColumn())) {
                    columnName = boardPropertyParam.getColumn().getColumnName();
                }
                if (!Objects.isNull(boardPropertyParam.getTableId())) {
                    indicatorTable = tableList.get(boardPropertyParam.getTableId());
                }
            }
            SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
            Function measureFunc = new Function();
            measureFunc.setName(IndicatorMeasureEnum.COUNT.name());
            if (!StringUtils.isEmpty(e.getMeasure()) && !IndicatorMeasureEnum.DISTINCT.name().equals(e.getMeasure())) {
                measureFunc.setName(e.getMeasure());
            }
            List<Expression> measureExpList = Arrays.asList(new Column(indicatorTable, columnName));
            if (IndicatorMeasureEnum.DISTINCT.name().equals(e.getMeasure())) {
                Function distinctFunc = new Function();
                distinctFunc.setName(IndicatorMeasureEnum.DISTINCT.name());
                ExpressionList distinctFuncExpList = new ExpressionList();
                distinctFuncExpList.addExpressions(measureExpList);
                distinctFunc.setParameters(distinctFuncExpList);
                distinctFunc.isDistinct();
                measureExpList = Arrays.asList(distinctFunc);
            }
            ExpressionList measureFuncExpList = new ExpressionList();
            measureFuncExpList.setExpressions(measureExpList);
            measureFunc.setParameters(measureFuncExpList);
            selectExpressionItem.setExpression(measureFunc);
            selectExpressionItem.setAlias(new Alias(e.getAliasName()));
            return selectExpressionItem;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<Join> convertTableConnectToJoin(Map<Long, Table> tableList, List<BoardTableConnectParam> tableConnectList) {
        return tableConnectList.stream().skip(1).map(e -> {
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
    }

    private static Collection<? extends SelectItem> convertDimensionToSelectItems(Map<Long, Table> tableList, List<BoardAnalysisDimensionParam> dimensionList) {
        return dimensionList.stream().map(e -> {
            BoardPropertyParam boardPropertyParam = e.getProperty();
            String columnName = boardPropertyParam.getColumn().getColumnName();
            String expression = columnName;
            Table curTable = tableList.get(boardPropertyParam.getTableId());
            SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
            selectExpressionItem.setExpression(new Column(curTable, expression));
            selectExpressionItem.setAlias(new Alias(e.getAliasName()));
            return selectExpressionItem;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<OrderByElement> convertDimensionToOrderByElements(Map<Long, Table> tableList, List<BoardAnalysisDimensionParam> dimensionList) {
        return dimensionList.stream().map(e -> {
            if (DimensionTypeEnum.ROW.name().equals(e.getType())) {
                BoardPropertyParam boardPropertyParam = e.getProperty();
                String columnName = boardPropertyParam.getColumn().getColumnName();
                String expression = columnName;
                Table curTable = tableList.get(boardPropertyParam.getTableId());
                OrderByElement orderByElement = new OrderByElement();
                orderByElement.setAsc(true);
                orderByElement.setExpression(new Column(curTable, columnName));
                return orderByElement;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static GroupByElement convertDimensionToGroupByElement(Map<Long, Table> tableList, List<BoardAnalysisDimensionParam> dimensionList) {
        GroupByElement groupByElement = new GroupByElement();
        List<Expression> groupByExpressions = dimensionList.stream().map(e -> {
            BoardPropertyParam boardPropertyParam = e.getProperty();
            String columnName = boardPropertyParam.getColumn().getColumnName();
            String expression = columnName;
            Table curTable = tableList.get(boardPropertyParam.getTableId());
            //分组
            return new Column(curTable, expression);
        }).collect(Collectors.toList());
        groupByElement.setGroupByExpressions(groupByExpressions);
        return groupByElement;
    }

}