
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.core.pojo.base.entity.BaseEntity;
import lombok.Data;

/**
 * 属性值
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:45
 */
@Data
@TableName("tbl_board_property_value")
public class BoardPropertyValue extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

}
