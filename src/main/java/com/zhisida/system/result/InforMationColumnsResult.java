
package com.zhisida.system.result;

import lombok.Data;

/**
 * 数据库表中返回对象
 *
 * @author Young-Pastor
 */
@Data
public class InforMationColumnsResult {

    /**
     * 字段名
     */
    public String columnName;

    /**
     * 数据库中类型
     */
    public String dataType;

    /**
     * 字段描述
     */
    public String columnComment;

    /**
     * 主外键
     */
    public String columnKey;

}
