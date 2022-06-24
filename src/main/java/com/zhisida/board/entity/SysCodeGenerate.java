
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;

/**
 * 代码生成基础配置
 *
 * @author Young-Pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_code_generate")
public class SysCodeGenerate extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 类名
     */
    private String className;

    /**
     * 是否移除表前缀
     */
    private String tablePrefix;

    /**
     * 生成方式
     */
    private String generateType;

    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 包名
     */
    private String packageName;

    /**
     * 业务名（业务代码包名称）
     */
    private String busName;

    /**
     * 功能名（数据库表名称）
     */
    private String tableComment;

    /**
     * 所属应用
     */
    private String appCode;

    /**
     * 菜单上级
     */
    private String menuPid;

}
