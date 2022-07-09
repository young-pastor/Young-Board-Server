
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * 数据源配置表
 *
 * @author Young-Pastor
 * @date 2022-06-17 15:08:24
 */
@Data
@TableName("tbl_board_data_source")
public class BoardDataSource extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据源名称
     */
    @Excel(name = "数据源名称")
    private String displayName;

    /**
     * 数据库配置
     */
    @Excel(name = "数据库配置")
    private String config;

    /**
     * 数据库类型
     */
    @Excel(name = "数据库类型")
    private String type;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    private String tablePrefix;

    private String tableSubfix;

    private String primaryKeys;
}
