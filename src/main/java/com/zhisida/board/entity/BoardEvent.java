
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 元事件配置
 *
 * @author young-pastor
 * @date 2022-06-20 11:52:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_event")
public class BoardEvent extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 事件分组
     */
    @Excel(name = "事件分组")
    private String eventGorupId;

    /**
     * 事件名称
     */
    @Excel(name = "事件名称")
    private String displayName;

    /**
     * 表字段ID
     */
    @Excel(name = "表字段ID")
    private Long tableColumnId;

    /**
     * 计算方式
     */
    @Excel(name = "计算方式")
    private String measure;

    /**
     * 事件值
     */
    @Excel(name = "事件值")
    private String value;

    /**
     * 事件值类型
     */
    @Excel(name = "事件值类型")
    private String valueType;

}
