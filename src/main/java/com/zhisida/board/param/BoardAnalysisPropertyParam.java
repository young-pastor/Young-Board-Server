package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 实时分析属性表参数类
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:05
 */
@Data
public class BoardAnalysisPropertyParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 属性ID
     */
    @NotNull(message = "属性ID不能为空，请检查propertyId参数", groups = {add.class, edit.class})
    private Long propertyId;

    /**
     *
     */
    @NotNull(message = "不能为空，请检查analysisEventId参数", groups = {add.class, edit.class})
    private Long analysisEventId;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数", groups = {add.class, edit.class})
    private Long sort;

    /**
     *
     */
    @NotBlank(message = "不能为空，请检查subLogic参数", groups = {add.class, edit.class})
    private String subLogic;

    private String measure;

    private String displayName;

    private BoardPropertyParam property;

    private String aliasName;
}
