
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统职位表
 *
 * @author young-pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_pos")
public class SysPos extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 20)
    private String name;

    /**
     * 编码
     */
    @Excel(name = "编码", width = 20)
    private String code;

    /**
     * 排序
     */
    @Excel(name = "排序", width = 20)
    private Integer sort;

    /**
     * 备注
     */
    @Excel(name = "备注", width = 20)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String remark;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @Excel(name = "状态", replace = {"正常_0", "停用_1", "删除_2"}, width = 20)
    private Integer status;
}
