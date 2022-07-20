package com.zhisida.board.analysis.provider;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.zhisida.board.analysis.enums.*;
import com.zhisida.board.param.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
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


public class JdbcOriginQueryUtil {

    public static String convertAnalysisToSql(BoardAnalysisParam analysisParam, DbType dbType) {
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
//            selectItems.addAll(convertEventToSelectItems(mainTable, tableList, analysisParam.getEventList()));
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

    private static Collection<? extends SelectItem> convertEventToSelectItems(Table mainTable, Map<Long, Table> tableList, List<BoardAnalysisEventParam> eventList) {
        return eventList.stream().filter(e -> Objects.nonNull(e.getProperty())).map(a -> {
            BoardAnalysisPropertyParam e = a.getProperty();
            BoardPropertyParam boardPropertyParam = e.getProperty();
            String columnName = "*";
            Table indicatorTable = null;
            if (!Objects.isNull(boardPropertyParam)) {
                if (!Objects.isNull(boardPropertyParam.getColumn())) {
                    columnName = boardPropertyParam.getColumn().getColumnName();
                    indicatorTable = mainTable;
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

    private static Expression convertFilterToWhereExpression(String parentLogic, Map<Long, Table> tableList, List<BoardAnalysisFilterParam> filterList) {
        Expression filterExp = null;
        for (int i = 0; i < filterList.size(); i++) {
            //TODO 需要排列条件
            BoardAnalysisFilterParam e = filterList.get(i);
            BoardPropertyParam propertyParam = e.getProperty();

            Expression condition = null;
            FilterMeasureEnum measure = FilterMeasureEnum.valueOf(e.getMeasure());
            Column conditionColumn = new Column(tableList.get(propertyParam.getTableId()), propertyParam.getColumn().getColumnName());
            StringValue conditionValue = null;
            if (!StringUtils.isEmpty(propertyParam.getValue())) {
                conditionValue = new StringValue(propertyParam.getValue());
            }
            switch (measure) {
                case IS_NULL:     //为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(conditionColumn);
                    break;
                case NOT_NULL:    //不为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(conditionColumn);
                    ((IsNullExpression) condition).setNot(true);
                    break;
                case IS_EMPTY:    //没值
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(conditionColumn);
                    ((EqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case NOT_EMPTY:   //有值
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(conditionColumn);
                    ((NotEqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case IS_TRUE:    //为真
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(conditionColumn);
                    ((IsBooleanExpression) condition).setIsTrue(true);
                    break;
                case IS_FALSE:   //为假
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(conditionColumn);
                    ((IsBooleanExpression) condition).setIsTrue(false);
                    break;
                case EQUAL:       //等于
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(conditionColumn);
                    ((EqualsTo) condition).setRightExpression(conditionValue);
                    break;
                case NOT_EQUAL:   //不等于
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(conditionColumn);
                    ((NotEqualsTo) condition).setRightExpression(conditionValue);
                    break;
                case LESS_THAN:         //小于
                    condition = new MinorThan();
                    ((MinorThan) condition).setLeftExpression(conditionColumn);
                    ((MinorThan) condition).setRightExpression(conditionValue);
                    break;
                case LESS_THAN_EQUAL:   //小于等于
                    condition = new MinorThanEquals();
                    ((MinorThanEquals) condition).setLeftExpression(conditionColumn);
                    ((MinorThanEquals) condition).setRightExpression(conditionValue);
                    break;
                case GREATER_THAN:      //大于
                    condition = new GreaterThan();
                    ((GreaterThan) condition).setLeftExpression(conditionColumn);
                    ((GreaterThan) condition).setRightExpression(conditionValue);
                    break;
                case GREATER_THAN_EQUAL://大于等于
                    condition = new GreaterThanEquals();
                    ((GreaterThanEquals) condition).setLeftExpression(conditionColumn);
                    ((GreaterThanEquals) condition).setRightExpression(conditionValue);
                    break;
                case RANGE:             //范围
                    condition = new Between();
                    String[] values = propertyParam.getValue().split(",");
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(values[0]));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(values[1]));
                    break;
                case IN:                //全包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(conditionColumn);
                    ((InExpression) condition).setRightExpression(conditionValue);
                    break;
                case NOT_IN:            //全不包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(conditionColumn);
                    ((InExpression) condition).setRightExpression(conditionValue);
                    ((InExpression) condition).setNot(true);
                    break;
                case LIKE:              //包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    break;
                case LEFT_LIKE:         //左包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    break;
                case RIGHT_LIKE:        //右包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    break;
                case NOT_LIKE:          //不包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    ((LikeExpression) condition).setNot(true);
                    break;
                case MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case MATCH_IGNORE_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case NOT_MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case NOT_MATCH_IGNORE_CASE:         //正则不匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case LAST_SEVEN_DAY:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), -6))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_FOURTEEN_DAY:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), -13))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_MONTH:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -1))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_QUARTER:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -3))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_HAFT_YEAR:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -6))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_YEAR:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -12))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
            }

            if (i < 1) {
                filterExp = condition;
            } else {
                BinaryExpression binaryExpression = null;
                if (FilterLogicEnum.OR.name().equals(parentLogic)) {
                    binaryExpression = new OrExpression();
                } else {
                    binaryExpression = new AndExpression();
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
            Column conditionColumn = new Column(tableList.get(eventParam.getTableId()), eventParam.getColumn().getColumnName());
            StringValue conditionValue = null;
            if (!StringUtils.isEmpty(eventParam.getValue())) {
                conditionValue = new StringValue(eventParam.getValue());
            }
            switch (measure) {
                case IS_NULL:     //为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(conditionColumn);
                    break;
                case NOT_NULL:    //不为空
                    condition = new IsNullExpression();
                    ((IsNullExpression) condition).setLeftExpression(conditionColumn);
                    ((IsNullExpression) condition).setNot(true);
                    break;
                case IS_EMPTY:    //没值
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(conditionColumn);
                    ((EqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case NOT_EMPTY:   //有值
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(conditionColumn);
                    ((NotEqualsTo) condition).setRightExpression(new StringValue(""));
                    break;
                case IS_TRUE:    //为真
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(conditionColumn);
                    ((IsBooleanExpression) condition).setIsTrue(true);
                    break;
                case IS_FALSE:   //为假
                    condition = new IsBooleanExpression();
                    ((IsBooleanExpression) condition).setLeftExpression(conditionColumn);
                    ((IsBooleanExpression) condition).setIsTrue(false);
                    break;
                case EQUAL:       //等于
                    condition = new EqualsTo();
                    ((EqualsTo) condition).setLeftExpression(conditionColumn);
                    ((EqualsTo) condition).setRightExpression(conditionValue);
                    break;
                case NOT_EQUAL:   //不等于
                    condition = new NotEqualsTo();
                    ((NotEqualsTo) condition).setLeftExpression(conditionColumn);
                    ((NotEqualsTo) condition).setRightExpression(conditionValue);
                    break;
                case LESS_THAN:         //小于
                    condition = new MinorThan();
                    ((MinorThan) condition).setLeftExpression(conditionColumn);
                    ((MinorThan) condition).setRightExpression(conditionValue);
                    break;
                case LESS_THAN_EQUAL:   //小于等于
                    condition = new MinorThanEquals();
                    ((MinorThanEquals) condition).setLeftExpression(conditionColumn);
                    ((MinorThanEquals) condition).setRightExpression(conditionValue);
                    break;
                case GREATER_THAN:      //大于
                    condition = new GreaterThan();
                    ((GreaterThan) condition).setLeftExpression(conditionColumn);
                    ((GreaterThan) condition).setRightExpression(conditionValue);
                    break;
                case GREATER_THAN_EQUAL://大于等于
                    condition = new GreaterThanEquals();
                    ((GreaterThanEquals) condition).setLeftExpression(conditionColumn);
                    ((GreaterThanEquals) condition).setRightExpression(conditionValue);
                    break;
                case RANGE:             //范围
                    condition = new Between();
                    String[] values = eventParam.getValue().split(",");
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(values[0]));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(values[1]));
                    break;
                case IN:                //全包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(conditionColumn);
                    ((InExpression) condition).setRightExpression(conditionValue);
                    break;
                case NOT_IN:            //全不包含
                    condition = new InExpression();
                    ((InExpression) condition).setLeftExpression(conditionColumn);
                    ((InExpression) condition).setRightExpression(conditionValue);
                    ((InExpression) condition).setNot(true);
                    break;
                case LIKE:              //包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    break;
                case LEFT_LIKE:         //左包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    break;
                case RIGHT_LIKE:        //右包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    break;
                case NOT_LIKE:          //不包含
                    condition = new LikeExpression();
                    ((LikeExpression) condition).setLeftExpression(conditionColumn);
                    ((LikeExpression) condition).setRightExpression(conditionValue);
                    ((LikeExpression) condition).setNot(true);
                    break;
                case MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case MATCH_IGNORE_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case NOT_MATCH_CASE:             //正则匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASESENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case NOT_MATCH_IGNORE_CASE:         //正则不匹配
                    condition = new RegExpMatchOperator(RegExpMatchOperatorType.NOT_MATCH_CASEINSENSITIVE);
                    ((RegExpMatchOperator) condition).setLeftExpression(conditionColumn);
                    ((RegExpMatchOperator) condition).setRightExpression(conditionValue);
                    break;
                case LAST_SEVEN_DAY:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), -6))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_FOURTEEN_DAY:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), -13))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_MONTH:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -1))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_QUARTER:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -3))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_HAFT_YEAR:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -6))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
                    break;
                case LAST_YEAR:
                    condition = new Between();
                    ((Between) condition).setLeftExpression(conditionColumn);
                    ((Between) condition).setBetweenExpressionStart(new StringValue(DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -12))));
                    ((Between) condition).setBetweenExpressionEnd(new StringValue(DateUtil.formatDate(DateUtil.offsetDay(new Date(), 1))));
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
            Table indicatorTable = null;
            if (!Objects.isNull(boardPropertyParam)) {
                if (!Objects.isNull(boardPropertyParam.getColumn())) {
                    columnName = boardPropertyParam.getColumn().getColumnName();
                    indicatorTable = mainTable;
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
            Table curTable = tableList.get(boardPropertyParam.getTableId());
            Expression expression = new Column(curTable, columnName);
            if (ColumnTypeEnum.DATE_TIME.name().equals(e.getProperty().getColumn().getColumnType())) {
                String dateFormat = "%Y-%m-%d";
                String unit = e.getUnit();
                if (!StringUtils.isEmpty(unit)) {
                    DateTimeUnitEnum unitEnum = DateTimeUnitEnum.valueOf(unit);
                    switch (unitEnum) {
                        case SECOND:
                            dateFormat = "%Y-%m-%d %H:%m:%s";
                            break;
                        case MINUTE:
                            dateFormat = "%Y-%m-%d %H:%m";
                            break;
                        case HOUR:
                            dateFormat = "%Y-%m-%d %H";
                            break;
                        case DAY:
                            dateFormat = "%Y-%m-%d";
                            break;
                        case WEEK:
                            dateFormat = "%Y-%u";
                            break;
                        case MONTH:
                            dateFormat = "%Y-%m";
                            break;
                        case YEAR:
                            dateFormat = "%Y";
                            break;
                    }
                }
                ExpressionList measureExpList = new ExpressionList(expression, new StringValue(dateFormat));
                Function dateTimeFunc = new Function();
                dateTimeFunc.setName("DATE_FORMAT");
                dateTimeFunc.setParameters(measureExpList);
                expression = dateTimeFunc;
            } else if (ColumnTypeEnum.NUMBER.name().equals(e.getProperty().getColumn().getColumnType())) {
                String unitType = e.getProperty().getUnitType();
                if (StringUtils.hasLength(e.getUnitType())) {
                    unitType = e.getUnitType();
                }
                String unit = e.getProperty().getUnit();
                if (StringUtils.hasLength(e.getUnit())) {
                    unit = e.getUnit();
                }
                if (!StringUtils.isEmpty(e.getUnit()) && !StringUtils.isEmpty(unitType)) {
                    PropertyUnitTypeEnum pUnitType = PropertyUnitTypeEnum.valueOf(unitType);
                    switch (pUnitType) {
                        case STEP:
                            Expression unitExpression = new LongValue(unit);
                            if (unit.contains(".")) {
                                unitExpression = new DoubleValue(unit);
                            }
                            Subtraction leftExp = new Subtraction();
                            leftExp.setLeftExpression(expression);
                            Modulo modExp = new Modulo();
                            modExp.setLeftExpression(expression);
                            modExp.setRightExpression(unitExpression);
                            leftExp.setRightExpression(modExp);
                            Addition rightExp = new Addition();
                            rightExp.setLeftExpression(expression);
                            Subtraction sub = new Subtraction();
                            sub.setLeftExpression(unitExpression);
                            Modulo modExp2 = new Modulo();
                            modExp2.setLeftExpression(expression);
                            modExp2.setRightExpression(unitExpression);
                            sub.setRightExpression(modExp2);
                            rightExp.setRightExpression(sub);
                            ExpressionList unitExpList = new ExpressionList(leftExp, new StringValue("~"), rightExp);
                            Function concatFunc = new Function();
                            concatFunc.setName("CONCAT");
                            concatFunc.setParameters(unitExpList);
                            expression = concatFunc;
                            break;
                        case SELF:
                            List<List> unitList = JSONUtil.toList(unit, List.class);
                            if (CollectionUtils.isEmpty(unitList)) {
                                break;
                            }
                            List<WhenClause> whenClauses = new ArrayList<>();
                            for (int i = 0; i < unitList.size(); i++) {
                                List units = unitList.get(i);
                                if (CollectionUtils.isEmpty(units) || units.size() > 2) {
                                    continue;
                                }
                                Expression whenExp = null;
                                String thenValue = null;
                                if (i == 0 && (units.size() == 1 || units.get(0) instanceof String)) {
                                    String rightRealValue = String.valueOf(units.get(0));
                                    if (units.size() == 2) {
                                        rightRealValue = String.valueOf(units.get(1));
                                    }
                                    thenValue = "-∞ ~ " + rightRealValue;
                                    Expression rightValueExp = new LongValue(rightRealValue);
                                    if (rightRealValue.contains(".")) {
                                        rightValueExp = new DoubleValue(rightRealValue);
                                    }
//                                    c<1
                                    MinorThan rightWhenExp = new MinorThan();
                                    rightWhenExp.setLeftExpression(expression);
                                    rightWhenExp.setRightExpression(rightValueExp);
                                    whenExp = rightWhenExp;
                                } else if (i == unitList.size() - 1) {
                                    //设置一个条件
                                    String realValue = String.valueOf(units.get(0));
                                    thenValue = realValue = " ~ +∞";
                                    Expression rightValueExp = new LongValue(realValue);
                                    if (realValue.contains(".")) {
                                        rightValueExp = new DoubleValue(realValue);
                                    }
                                    GreaterThanEquals leftWhenExp = new GreaterThanEquals();
                                    leftWhenExp.setLeftExpression(expression);
                                    leftWhenExp.setRightExpression(rightValueExp);
                                    whenExp = leftWhenExp;
                                } else {
                                    String leftRealValue = String.valueOf(units.get(0));
                                    String rightRealValue = String.valueOf(units.get(1));
                                    thenValue = leftRealValue = " ~ " + rightRealValue;
                                    Expression leftValueExp = new LongValue(leftRealValue);
                                    if (leftRealValue.contains(".")) {
                                        leftValueExp = new DoubleValue(leftRealValue);
                                    }
                                    Expression rightValueExp = new LongValue(rightRealValue);
                                    if (rightRealValue.contains(".")) {
                                        rightValueExp = new DoubleValue(rightRealValue);
                                    }

                                    AndExpression andExpression = new AndExpression();
                                    GreaterThanEquals leftWhenExp = new GreaterThanEquals();
                                    leftWhenExp.setLeftExpression(expression);
                                    leftWhenExp.setRightExpression(rightValueExp);

                                    MinorThan rightWhenExp = new MinorThan();
                                    rightWhenExp.setLeftExpression(expression);
                                    rightWhenExp.setRightExpression(rightValueExp);

                                    andExpression.setLeftExpression(leftWhenExp);
                                    andExpression.setRightExpression(rightWhenExp);
                                    whenExp = andExpression;
                                }
                                WhenClause whenClause = new WhenClause();
                                whenClause.setThenExpression(new StringValue(thenValue));
                                whenClause.setWhenExpression(whenExp);
                                whenClauses.add(whenClause);
                            }
                            CaseExpression caseExpression = new CaseExpression();
                            caseExpression.setElseExpression(new StringValue("UNKNOWN"));
                            caseExpression.setWhenClauses(whenClauses);
                            expression = caseExpression;
                            break;
                    }
                }
            }
            SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
            selectExpressionItem.setExpression(expression);
            selectExpressionItem.setAlias(new Alias(e.getAliasName()));
            return selectExpressionItem;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static List<OrderByElement> convertDimensionToOrderByElements(Map<Long, Table> tableList, List<BoardAnalysisDimensionParam> dimensionList) {
        return dimensionList.stream().map(e -> {
            if (DimensionTypeEnum.ROW.name().equals(e.getType())) {
                OrderByElement orderByElement = new OrderByElement();
                orderByElement.setAsc(true);
                orderByElement.setExpression(new Column(e.getAliasName()));
                return orderByElement;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static GroupByElement convertDimensionToGroupByElement(Map<Long, Table> tableList, List<BoardAnalysisDimensionParam> dimensionList) {
        GroupByElement groupByElement = new GroupByElement();
        List<Expression> groupByExpressions = dimensionList.stream().map(e -> new Column(e.getAliasName())).collect(Collectors.toList());
        groupByElement.setGroupByExpressions(groupByExpressions);
        return groupByElement;
    }

}
