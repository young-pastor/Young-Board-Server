package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 实时分析分组属性参数类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:03
*/
@Data
public class BoardAnalysisDimensionParam extends BaseParam {

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
     * 实时分析事件ID
     */
    @NotNull(message = "实时分析事件ID不能为空，请检查analysisEventId参数", groups = {add.class, edit.class})
    private Long analysisEventId;

    /**
     * 实时分析属性ID
     */
    @NotNull(message = "实时分析属性ID不能为空，请检查analysisPropertyId参数", groups = {add.class, edit.class})
    private Long analysisPropertyId;

    /**
     * 属性ID
     */
    @NotNull(message = "属性ID不能为空，请检查propertyId参数", groups = {add.class, edit.class})
    private Long propertyId;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数", groups = {add.class, edit.class})
    private Integer sort;

    /**
     * 分组单位
     */
    @NotBlank(message = "分组单位不能为空，请检查unit参数", groups = {add.class, edit.class})
    private String unit;

    /**
     * 分组单位类型
     */
    @NotBlank(message = "分组单位类型不能为空，请检查unitType参数", groups = {add.class, edit.class})
    private String unitType;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空，请检查type参数", groups = {add.class, edit.class})
    private String type;

    private BoardPropertyParam property;
    private String aliasName;
}
