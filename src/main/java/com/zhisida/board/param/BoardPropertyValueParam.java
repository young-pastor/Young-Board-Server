
package com.zhisida.board.param;

import com.zhisida.core.pojo.base.param.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
* 属性值参数类
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:57:45
*/
@Data
public class BoardPropertyValueParam extends BaseParam {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

}
