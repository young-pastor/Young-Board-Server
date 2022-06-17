
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 员工职位关联表
 *
 * @author young-pastor
 */
@Data
@TableName("sys_emp_pos")
public class SysEmpPos {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 员工id
     */
    private Long empId;

    /**
     * 职位id
     */
    private Long posId;
}
