
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 字段关联配置
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:45:42
 */
@Data
@TableName("tbl_board_table_connect")
public class BoardTableConnect implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long tableId;
    /**
     * 字段ID
     */
    @Excel(name = "字段ID")
    private Long columnId;

    private Long connectTableId;
    /**
     * 关联字段ID
     */
    @Excel(name = "关联字段ID")
    private Long connectColumnId;

    /**
     * 关联类型
     */
    @Excel(name = "关联类型")
    private String connectType;

}
