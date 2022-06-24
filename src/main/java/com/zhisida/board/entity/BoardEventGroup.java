
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 元事件分组
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_event_group")
public class BoardEventGroup extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 事件名称
     */
    @Excel(name = "事件名称")
    private String displayName;

}
