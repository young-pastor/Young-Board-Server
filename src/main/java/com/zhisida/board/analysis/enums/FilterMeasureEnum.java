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
    GREATER_THAN,//大于
    RANGE,       //范围

    CONTAIN ,    //包含
    LEFT_CONTAIN,//左包含
    RIGHT_CONTAIN,//右包含
    NOT_CONTAIN,  //不包含
    MATCH,        //正则匹配
    NOT_MATCH
}
