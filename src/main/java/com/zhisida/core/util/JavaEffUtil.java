
package com.zhisida.core.util;

/**
 * java与effect工具类
 *
 * @author Young-Pastor
 */
public class JavaEffUtil {

    /**
     * java转显示类型
     *
     * @author Young-Pastor
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
