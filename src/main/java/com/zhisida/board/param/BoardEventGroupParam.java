
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
* 元事件分组参数类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
*/
@Data
public class BoardEventGroupParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 事件名称
     */
    @NotBlank(message = "事件名称不能为空，请检查displayName参数", groups = {add.class, edit.class})
    private String displayName;

}
