
package com.zhisida.board.core.enums;

import lombok.Getter;

/**
 * 不同数据库类型的枚举
 * <p>
 * 用于标识mapping.xml中不同数据库的标识
 *
 * @author Young-Pastor
 */
@Getter
public enum DbIdEnum {

    /**
     * mysql
     */
    MYSQL("mysql", "mysql"),

    /**
     * pgsql
     */
    PG_SQL("pgsql", "postgresql"),

    /**
     * oracle
     */
    ORACLE("oracle", "oracle"),

    /**
     * mssql
     */
    MS_SQL("mssql", "sqlserver"),

    /**
     * 达梦数据库
     */
    DM_SQL("dm", "dm"),

    /**
     * 人大金仓数据库
     */
    KINGBASE_ES("kingbase", "kingbasees");

    private final String code;

    private final String name;

    DbIdEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
