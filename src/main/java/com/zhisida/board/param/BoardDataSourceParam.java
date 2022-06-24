
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
* 数据源配置表参数类
 *
 * @author Young-Pastor
 * @date 2022-06-17 15:08:24
*/
@Data
public class BoardDataSourceParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 数据源名称
     */
    @NotBlank(message = "数据源名称不能为空，请检查displayName参数", groups = {add.class, edit.class})
    private String displayName;

    /**
     * 数据库配置
     */
    @NotBlank(message = "数据库配置不能为空，请检查config参数", groups = {add.class, edit.class})
    private String config;

    /**
     * 数据库类型
     */
    @NotBlank(message = "数据库类型不能为空，请检查type参数", groups = {add.class, edit.class})
    private String type;

    /**
     * 备注
     */
    private String remark;

}
