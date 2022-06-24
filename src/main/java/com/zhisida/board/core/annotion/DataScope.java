
package com.zhisida.board.core.annotion;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author Young-Pastor
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScope {
}
