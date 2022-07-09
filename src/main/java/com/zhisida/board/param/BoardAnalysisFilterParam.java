
package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实时分析条件参数类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:20:35
 */
@Data
public class BoardAnalysisFilterParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 父级条件ID
     */
    @NotNull(message = "父级条件ID不能为空，请检查parentId参数", groups = {add.class, edit.class})
    private Long parentId;

    /**
     * 分析事件ID
     */
    @NotNull(message = "分析事件ID不能为空，请检查analysisEventId参数", groups = {add.class, edit.class})
    private Long analysisEventId;

    /**
     * 分析属性ID
     */
    @NotNull(message = "分析属性ID不能为空，请检查analysisPropertyId参数", groups = {add.class, edit.class})
    private Long analysisPropertyId;

    /**
     * 实时分析事件ID
     */
    @NotNull(message = "实时分析事件ID不能为空，请检查analysisId参数", groups = {add.class, edit.class})
    private Long analysisId;

    /**
     * 属性ID
     */
    @NotNull(message = "属性ID不能为空，请检查propertyId参数", groups = {add.class, edit.class})
    private Long propertyId;

    /**
     * 子级条件逻辑
     */
    @NotBlank(message = "子级条件逻辑不能为空，请检查subLogic参数", groups = {add.class, edit.class})
    private String subLogic;

    /**
     * 计算方式
     */
    @NotBlank(message = "计算方式不能为空，请检查measure参数", groups = {add.class, edit.class})
    private String measure;

    /**
     * 条件值
     */
    @NotBlank(message = "条件值不能为空，请检查value参数", groups = {add.class, edit.class})
    private String value;

    private BoardPropertyParam property;

}
