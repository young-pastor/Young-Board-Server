
package com.zhisida.board.core.util;

/**
 * java与effect工具类
 *
 * @author young-pastor
 */
public class JavaEffUtil {

    /**
     * java转显示类型
     *
     * @author young-pastor
     */
    public static String javaToEff (String javaType) {
        if( javaType == null || javaType.trim().length() == 0 ) return javaType;
        switch(javaType){
            case "String":return "input";
            case "Integer":return "input";
            case "Long":return "input";
            case "Date":return "datepicker";
            default:
                System.out.println(">>> 转化失败：未发现的类型" + javaType);
                break;
        }
        return javaType;
    }
}