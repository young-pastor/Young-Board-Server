
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实时分析
 *
 * @author Young-Pastor
 * @date 2022-07-07 21:06:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_analysis")
public class BoardAnalysis extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 展示名称
     */
    @Excel(name = "展示名称")
    private String displayName;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 图标配置
     */
    @Excel(name = "图标配置")
    private String chartConfig;

    /**
     * 条件逻辑
     */
    @Excel(name = "条件逻辑")
    private String subLogic;

    /**
     * 顺序
     */
    @Excel(name = "顺序")
    private Integer sort;

}
