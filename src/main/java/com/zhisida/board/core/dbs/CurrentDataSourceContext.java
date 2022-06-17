
package com.zhisida.board.core.dbs;

/**
 * datasource的上下文
 *
 * @author young-pastor
 */
public class CurrentDataSourceContext {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源类型
     *
     * @param dataSourceType 数据库类型
     */
    public static void setDataSourceType(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源类型
     *
     * @author young-pastor
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源类型
     *
     * @author young-pastor
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

}
