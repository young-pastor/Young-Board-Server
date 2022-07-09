
package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 实时分析参数类
 *
 * @author Young-Pastor
 * @date 2022-07-07 21:06:23
 */
@Data
public class BoardAnalysisParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 展示名称
     */
    @NotBlank(message = "展示名称不能为空，请检查displayName参数", groups = {add.class, edit.class})
    private String displayName;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空，请检查type参数", groups = {add.class, edit.class})
    private String type;

    /**
     * 图标配置
     */
    @NotBlank(message = "图标配置不能为空，请检查chartConfig参数", groups = {add.class, edit.class})
    private String chartConfig;

    /**
     * 条件逻辑
     */
    @NotBlank(message = "条件逻辑不能为空，请检查subLogic参数", groups = {add.class, edit.class})
    private String subLogic;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空，请检查sort参数", groups = {add.class, edit.class})
    private Integer sort;

    private List<BoardAnalysisEventParam> eventList;

    private List<BoardAnalysisPropertyParam> propertyList;

    private List<BoardAnalysisDimensionParam> dimensionList;

    private List<BoardAnalysisFilterParam> filterList;

    private List<BoardTableConnectParam> tableConnectList;
}
