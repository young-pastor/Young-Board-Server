
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 属性值
 *
 * @author young-pastor
 * @date 2022-06-20 11:57:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tbl_board_property_value")
public class BoardPropertyValue extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

}
