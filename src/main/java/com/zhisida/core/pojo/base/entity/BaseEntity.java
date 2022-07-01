
package com.zhisida.core.pojo.base.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用基础字段，需要此通用字段的实体可继承此类
 *
 * @author Young-Pastor
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Excel(name = "创建时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @Excel(name = "更新时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

}
