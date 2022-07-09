
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * 数据字段配置
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:27:41
 */
@Data
@TableName("tbl_board_table_column")
public class BoardTableColumn extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据表ID
     */
    @Excel(name = "数据表ID")
    private Long tableId;

    /**
     * 字段名称
     */
    @Excel(name = "字段名称")
    private String columnName;

    /**
     * 展示名称
     */
    @Excel(name = "展示名称")
    private String displayName;

    /**
     * 刷新方式
     */
    @Excel(name = "刷新方式")
    private String refreshType;

    /**
     * 字段类型
     */
    @Excel(name = "字段类型")
    private String columnType;

    /**
     * 数据类型
     */
    @Excel(name = "数据类型")
    private String dataType;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

}
