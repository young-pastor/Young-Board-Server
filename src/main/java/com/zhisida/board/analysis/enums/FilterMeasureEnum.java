package com.zhisida.board.analysis.enums;

public enum FilterMeasureEnum {

    IS_NULL,     //为空
    NOT_NULL,    //不为空
    IS_EMPTY,    //没值
    NOT_EMPTY,   //有值
    IS_TRUE,     //为真
    IS_FALSE,    //为假

    EQUAL,       //等于
    NOT_EQUAL,   //不等于
    LESS_THAN,   //小于
    LESS_THAN_EQUAL,   //小于等于
    GREATER_THAN,//大于
    GREATER_THAN_EQUAL,//大于等于
    RANGE,       //范围
    IN,
    NOT_IN,
    LIKE,    //包含
    LEFT_LIKE,//左包含
    RIGHT_LIKE,//右包含
    NOT_LIKE,  //不包含
    MATCH_CASE,        //正则匹配
    MATCH_IGNORE_CASE,
    NOT_MATCH_CASE,
    NOT_MATCH_IGNORE_CASE
}
