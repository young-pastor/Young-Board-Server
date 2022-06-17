
package com.zhisida.board.result;

import lombok.Data;

/**
 * 数据表返回对象
 *
 * @author young-pastor
 */
@Data
public class InformationResult {

    /**
     * 表名（字母形式的）
     */
    public String tableName;

    /**
     * 创建时间
     */
    public String createTime;

    /**
     * 更新时间
     */
    public String updateTime;

    /**
     * 表名称描述（注释）（功能名）
     */
    public String tableComment;

}
