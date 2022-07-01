
package com.zhisida.core.tenant.consts;

/**
 * 租户的常量
 *
 * @author Young-Pastor
 */
public interface TenantConstants {

    /**
     * 租户数据源标识前缀
     */
    String TENANT_DB_PREFIX = "tenant_db_";

    /**
     * 初始化租户的sql文件名称
     */
    String INIT_SQL_FILE_NAME = "tenant_init.sql";

    /**
     * 租户编码的字段名称
     */
    String TENANT_CODE = "tenantCode";

    /**
     * 租户对应的数据库编码字段名称
     */
    String TENANT_DB_NAME = "tenantDbName";

}
