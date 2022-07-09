
package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据表配置参数类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:17:36
 */
@Data
public class BoardTableParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 数据源ID
     */
    @NotNull(message = "数据源ID不能为空，请检查dataSourceId参数", groups = {add.class, edit.class})
    private Long dataSourceId;

    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空，请检查tableName参数", groups = {add.class, edit.class})
    private String tableName;

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
     * 备注
     */
    @NotBlank(message = "备注不能为空，请检查remark参数", groups = {add.class, edit.class})
    private String remark;

    private Boolean syncTable;
    private Boolean syncColumn;
    private Boolean syncConnect;

    private String aliasName;
}
