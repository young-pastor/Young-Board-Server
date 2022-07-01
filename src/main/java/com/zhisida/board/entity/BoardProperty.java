
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 属性配置
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:25
 */
@Data
@TableName("tbl_board_property")
public class BoardProperty implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 属性名称
     */
    @Excel(name = "属性名称")
    private String displayName;

    /**
     * 属性分组
     */
    @Excel(name = "属性分组")
    private Long propertyGroupId;

    private Long dataSourceId;

    private Long tableId;
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
     * 属性值
     */
    @Excel(name = "属性值")
    private String value;

    /**
     * 属性值类型
     */
    @Excel(name = "属性值类型")
    private String valueType;

    /**
     * 属性值
     */
    @Excel(name = "属性值")
    private String unit;

    /**
     * 属性值类型
     */
    @Excel(name = "属性值类型")
    private String unitType;

    /**
     * 属性值类型
     */
    @Excel(name = "属性值类型")
    private String isDefault;

    /**
     * 属性值类型
     */
    @Excel(name = "属性值类型")
    private String remark;

}
