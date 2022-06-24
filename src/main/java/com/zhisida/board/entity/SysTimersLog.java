
package com.zhisida.board.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务日志
 *
 * @author Young-Pastor
 * @date 2022-06-24 17:02:32
 */
@Data
@TableName("sys_timers_log")
public class SysTimersLog implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 任务编号
     */
    @Excel(name = "任务编号")
    private Long timerId;

    /**
     * 执行开始时间
     */
    @Excel(name = "执行开始时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date executeStartTime;

    /**
     * 执行结束时间
     */
    @Excel(name = "执行结束时间", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date executeEndTime;

    /**
     * 执行结果
     */
    @Excel(name = "执行结果")
    private String executeCode;

    /**
     * 执行信息
     */
    @Excel(name = "执行信息")
    private String executeMsg;

    /**
     * 执行参数
     */
    @Excel(name = "执行参数")
    private String executeParam;

}
