package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * 属性分组
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:37
 */
@Data
@TableName("tbl_board_property_group")
public class BoardPropertyGroup extends BaseEntity {

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
     * 分组名称
     */
    @Excel(name = "分组名称")
    private String displayName;

}
