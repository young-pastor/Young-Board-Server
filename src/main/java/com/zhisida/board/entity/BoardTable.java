
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据表配置
 *
 * @author young-pastor
 * @date 2022-06-20 11:17:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_table")
public class BoardTable extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据源ID
     */
    @Excel(name = "数据源ID")
    private Long dataSourceId;

    /**
     * 表名称
     */
    @Excel(name = "表名称")
    private String tableName;

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
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

}
