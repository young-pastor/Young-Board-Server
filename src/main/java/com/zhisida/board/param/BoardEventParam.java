
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
* 元事件配置参数类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:17
*/
@Data
public class BoardEventParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 事件分组
     */
    @NotBlank(message = "事件分组不能为空，请检查eventGorupId参数", groups = {add.class, edit.class})
    private String eventGorupId;

    /**
     * 事件名称
     */
    @NotBlank(message = "事件名称不能为空，请检查displayName参数", groups = {add.class, edit.class})
    private String displayName;

    /**
     * 表字段ID
     */
    @NotNull(message = "表字段ID不能为空，请检查tableColumnId参数", groups = {add.class, edit.class})
    private Long tableColumnId;

    /**
     * 计算方式
     */
    @NotBlank(message = "计算方式不能为空，请检查measure参数", groups = {add.class, edit.class})
    private String measure;

    /**
     * 事件值
     */
    @NotBlank(message = "事件值不能为空，请检查value参数", groups = {add.class, edit.class})
    private String value;

    /**
     * 事件值类型
     */
    @NotBlank(message = "事件值类型不能为空，请检查valueType参数", groups = {add.class, edit.class})
    private String valueType;

}
