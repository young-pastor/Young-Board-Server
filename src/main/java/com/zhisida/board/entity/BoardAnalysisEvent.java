
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实时分析事件
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:27:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_analysis_event")
public class BoardAnalysisEvent extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 实时分析ID
     */
    @Excel(name = "实时分析ID")
    private Long analysisId;

    /**
     * 事件ID
     */
    @Excel(name = "事件ID")
    private Long eventId;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     *
     */
    @Excel(name = "")
    private String subLogic;

    private String displayName;

}
