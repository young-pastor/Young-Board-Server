
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实时分析条件
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:20:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_analysis_filter")
public class BoardAnalysisFilter extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父级条件ID
     */
    @Excel(name = "父级条件ID")
    private Long parentId;

    /**
     * 分析事件ID
     */
    @Excel(name = "分析事件ID")
    private Long analysisEventId;

    /**
     * 分析属性ID
     */
    @Excel(name = "分析属性ID")
    private Long analysisPropertyId;

    /**
     * 实时分析事件ID
     */
    @Excel(name = "实时分析事件ID")
    private Long analysisId;

    /**
     * 属性ID
     */
    @Excel(name = "属性ID")
    private Long propertyId;

    /**
     * 子级条件逻辑
     */
    @Excel(name = "子级条件逻辑")
    private String subLogic;

    /**
     * 计算方式
     */
    @Excel(name = "计算方式")
    private String measure;

    /**
     * 条件值
     */
    @Excel(name = "条件值")
    private String value;

}
