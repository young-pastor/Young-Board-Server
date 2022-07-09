package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.zhisida.core.pojo.base.entity.BaseEntity;


/**
 * 实时分析分组属性
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_analysis_dimension")
public class BoardAnalysisDimension extends BaseEntity {

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
     * 实时分析事件ID
     */
    @Excel(name = "实时分析事件ID")
    private Long analysisEventId;

    /**
     * 实时分析属性ID
     */
    @Excel(name = "实时分析属性ID")
    private Long analysisPropertyId;

    /**
     * 属性ID
     */
    @Excel(name = "属性ID")
    private Long propertyId;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 分组单位
     */
    @Excel(name = "分组单位")
    private String unit;

    /**
     * 分组单位类型
     */
    @Excel(name = "分组单位类型")
    private String unitType;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

}
