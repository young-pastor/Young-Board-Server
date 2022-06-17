
package com.zhisida.board.core.tenant.context;

/**
 * 租户对应的数据库的临时存放
 *
 * @author young-pastor
 */
public class TenantDbNameHolder {

    private static final ThreadLocal<String> DB_NAME_HOLDER = new ThreadLocal<>();

    public static void put(String value) {
        DB_NAME_HOLDER.set(value);
    }

    public static String get() {
        return DB_NAME_HOLDER.get();
    }

    public static void remove() {
        DB_NAME_HOLDER.remove();
    }
}
