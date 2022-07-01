
package com.zhisida.core.util;

/**
 * java与sql工具类
 *
 * @author Young-Pastor
 */
public class JavaSqlUtil {

    /**
     * 数据类型转化JAVA
     *
     * @author Young-Pastor
     */
    public static String sqlToJava (String sqlType) {
        if( sqlType == null || sqlType.trim().length() == 0 ) return sqlType;
        sqlType = sqlType.toLowerCase();
        if(sqlType.startsWith("int")) {
            //如果以int开头，则直接返回int，兼容pgsql中int2 int8等
            return "Integer";
        }
        switch(sqlType){
            case "nvarchar":return "String";
            case "nvarchar2":return "String";
            case "char":return "String";
            case "varchar":return "String";
            case "enum":return "String";
            case "set":return "String";
            case "text":return "String";
            case "nchar":return "String";
            case "blob":return "byte[]";
            case "integer":return "Long";
            case "int":return "Integer";
            case "tinyint":return "Integer";
            case "smallint":return "Integer";
            case "mediumint":return "Integer";
            case "bit":return "Boolean";
            case "bigint":return "Long";
            case "number":return "Long";
            case "float":return "Float";
            case "double":return "Double";
            case "decimal":return "BigDecimal";
            case "boolean":return "Boolean";
            case "id":return "Long";
            case "date":return "Date";
            case "datetime":return "Date";
            case "year":return "Date";
            case "time":return "Time";
            case "timestamp":return "Timestamp";
            case "numeric":return "BigDecimal";
            case "real":return "BigDecimal";
            case "money":return "Double";
            case "smallmoney":return "Double";
            case "image":return "byte[]";
            default:
                System.out.println(">>> 转化失败：未发现的类型" + sqlType);
                break;
        }
        return sqlType;
    }
}
