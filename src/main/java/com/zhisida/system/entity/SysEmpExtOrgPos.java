
package com.zhisida.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 员工附属机构职位表
 *
 * @author Young-Pastor
 */
@Data
@TableName("sys_emp_ext_org_pos")
public class SysEmpExtOrgPos {

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
     * 机构id
     */
    private Long orgId;

    /**
     * 职位id
     */
    private Long posId;
}
