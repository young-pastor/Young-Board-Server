
package com.zhisida.board.param;

import com.zhisida.board.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 系统菜单参数
 *
 * @author young-pastor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysCacheParam extends BaseParam {
    /**
     * 主键
     */
    private Long parentId;

    private Long id;

    private String oldKey ;

    private String key ;

    private Object value;

    private String valueType;

    private Long time;
    private String timeUnit;

    public TimeUnit getCacheTimeUnit() {
        if (Objects.isNull(timeUnit)) {
            return TimeUnit.MILLISECONDS;
        }
        return TimeUnit.valueOf(timeUnit.toUpperCase());
    }
}
