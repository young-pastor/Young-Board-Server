package com.zhisida.board.analysis.enums;

import lombok.Getter;

@Getter
public enum IndicatorMeasureEnum {
    COUNT("总数"),     //总数 COUNT(*)
    SUM("总和"),       //总和 SUM(*)
    AVG("均值"),       //均值 AVG(*)
    MAX("最大值"),       //最大值 MAX(*)
    MIN("最小值"),       //最小值 MIN(*)
    DISTINCT("去重数"),  //去重数 COUNT(DISTINCT )

    USER("人数"),      //人数 DISTINCT USER
    COUNT_USER_AVG("人均次"),  //人均次  COUNT(*)/count(distinct user)
    SUN_USER_AVG("人均值");    //人均值 SUM(*)/count(distinct user)

    private String description;

    private IndicatorMeasureEnum(String description) {
        this.description = description;
    }
}
