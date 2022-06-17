
package com.zhisida.board.core.annotion;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author young-pastor
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScope {
}
