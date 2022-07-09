
package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 实时分析事件参数类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:27:10
*/
@Data
public class BoardAnalysisEventParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 实时分析ID
     */
    @NotNull(message = "实时分析ID不能为空，请检查analysisId参数", groups = {add.class, edit.class})
    private Long analysisId;

    /**
     * 事件ID
     */
    @NotNull(message = "事件ID不能为空，请检查eventId参数", groups = {add.class, edit.class})
    private Long eventId;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数", groups = {add.class, edit.class})
    private Integer sort;

    /**
     * 
     */
    @NotBlank(message = "不能为空，请检查subLogic参数", groups = {add.class, edit.class})
    private String subLogic;

    private BoardEventParam event;

}
