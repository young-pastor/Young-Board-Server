
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * 元事件分组
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
 */
@Data
@TableName("tbl_board_event_group")
public class BoardEventGroup extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 父ids
     */
    private String pids;

    /**
     * 事件名称
     */
    @Excel(name = "事件名称")
    private String displayName;

}
