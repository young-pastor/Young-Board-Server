
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
* 属性配置参数类
 *
 * @author young-pastor
 * @date 2022-06-20 11:57:25
*/
@Data
public class BoardPropertyParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 属性名称
     */
    @NotBlank(message = "属性名称不能为空，请检查displayName参数", groups = {add.class, edit.class})
    private String displayName;

    /**
     * 属性分组
     */
    @NotNull(message = "属性分组不能为空，请检查propertyGorupId参数", groups = {add.class, edit.class})
    private Long propertyGorupId;

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
     * 属性值
     */
    @NotBlank(message = "属性值不能为空，请检查value参数", groups = {add.class, edit.class})
    private String value;

    /**
     * 属性值类型
     */
    @NotBlank(message = "属性值类型不能为空，请检查valueType参数", groups = {add.class, edit.class})
    private String valueType;

    /**
     * 属性值
     */
    @NotBlank(message = "属性值不能为空，请检查unit参数", groups = {add.class, edit.class})
    private String unit;

    /**
     * 属性值类型
     */
    @NotBlank(message = "属性值类型不能为空，请检查unitType参数", groups = {add.class, edit.class})
    private String unitType;

    /**
     * 属性值类型
     */
    @NotBlank(message = "属性值类型不能为空，请检查isDefault参数", groups = {add.class, edit.class})
    private String isDefault;

    /**
     * 属性值类型
     */
    @NotBlank(message = "属性值类型不能为空，请检查remark参数", groups = {add.class, edit.class})
    private String remark;

}
