
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
* 数据字段配置参数类
 *
 * @author young-pastor
 * @date 2022-06-20 11:27:41
*/
@Data
public class BoardTableColumnParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 数据表ID
     */
    @NotNull(message = "数据表ID不能为空，请检查tableId参数", groups = {add.class, edit.class})
    private Long tableId;

    /**
     * 字段名称
     */
    @NotBlank(message = "字段名称不能为空，请检查columnName参数", groups = {add.class, edit.class})
    private String columnName;

    /**
     * 展示名称
     */
    @NotBlank(message = "展示名称不能为空，请检查displayName参数", groups = {add.class, edit.class})
    private String displayName;

    /**
     * 刷新方式
     */
    @NotBlank(message = "刷新方式不能为空，请检查refreshType参数", groups = {add.class, edit.class})
    private String refreshType;

    /**
     * 字段类型
     */
    @NotBlank(message = "字段类型不能为空，请检查columnType参数", groups = {add.class, edit.class})
    private String columnType;

    /**
     * 数据类型
     */
    @NotBlank(message = "数据类型不能为空，请检查dataType参数", groups = {add.class, edit.class})
    private String dataType;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空，请检查remark参数", groups = {add.class, edit.class})
    private String remark;

}
