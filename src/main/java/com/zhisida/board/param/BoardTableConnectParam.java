
package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 字段关联配置参数类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:45:42
 */
@Data
public class BoardTableConnectParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    private Long tableId;
    /**
     * 字段ID
     */
    @NotNull(message = "字段ID不能为空，请检查columnId参数", groups = {add.class, edit.class})
    private Long columnId;

    private Long connectTableId;

    /**
     * 关联字段ID
     */
    @NotNull(message = "关联字段ID不能为空，请检查connectColumnId参数", groups = {add.class, edit.class})
    private Long connectColumnId;

    /**
     * 关联类型
     */
    @NotBlank(message = "关联类型不能为空，请检查connectType参数", groups = {add.class, edit.class})
    private String connectType;

}
