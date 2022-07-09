package com.zhisida.board.analysis.enums;

public enum IndicatorMeasureEnum {
    COUNT,     //总数 COUNT(*)
    SUN,       //总和 SUM(*)
    AVG,       //均值 AVG(*)
    MAX,       //最大值 MAX(*)
    MIN,       //最小值 MIN(*)
    DISTINCT,  //去重复数 COUNT(DISTINCT )

    USER,      //人数 DISTINCT USER
    COUNT_USER_AVG,  //人均次  COUNT(*)/count(distinct user)
    SUN_USER_AVG,    //人均值 SUM(*)/count(distinct user)

}
