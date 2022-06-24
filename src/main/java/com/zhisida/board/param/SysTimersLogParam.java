
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
* 任务日志参数类
 *
 * @author Young-Pastor
 * @date 2022-06-24 17:02:32
*/
@Data
public class SysTimersLogParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 任务编号
     */
    @NotNull(message = "任务编号不能为空，请检查timerId参数", groups = {add.class, edit.class})
    private Long timerId;

    /**
     * 执行开始时间
     */
    @NotNull(message = "执行开始时间不能为空，请检查executeStartTime参数", groups = {add.class, edit.class})
    private String executeStartTime;

    /**
     * 执行结束时间
     */
    @NotNull(message = "执行结束时间不能为空，请检查executeEndTime参数", groups = {add.class, edit.class})
    private String executeEndTime;

    /**
     * 执行结果
     */
    @NotBlank(message = "执行结果不能为空，请检查executeCode参数", groups = {add.class, edit.class})
    private String executeCode;

    /**
     * 执行信息
     */
    @NotBlank(message = "执行信息不能为空，请检查executeMsg参数", groups = {add.class, edit.class})
    private String executeMsg;

    /**
     * 执行参数
     */
    @NotBlank(message = "执行参数不能为空，请检查executeParam参数", groups = {add.class, edit.class})
    private String executeParam;

}
