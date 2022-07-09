package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.zhisida.core.pojo.base.entity.BaseEntity;


/**
 * 实时分析属性表
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_analysis_property")
public class BoardAnalysisProperty extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    private Long analysisId;

    /**
     * 属性ID
     */
    @Excel(name = "属性ID")
    private Long propertyId;

    /**
     * 
     */
    @Excel(name = "")
    private Long analysisEventId;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 
     */
    @Excel(name = "")
    private String subLogic;

}
