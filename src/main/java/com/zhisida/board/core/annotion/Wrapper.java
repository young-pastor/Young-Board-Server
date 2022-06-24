
package com.zhisida.board.core.annotion;

import com.zhisida.board.core.pojo.base.wrapper.BaseWrapper;

import java.lang.annotation.*;

/**
 * 结果包装的注解，一般用在Controller层，给最后响应结果做包装
 *
 * @author Young-Pastor
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Wrapper {

    /**
     * 具体包装类
     */
    Class<? extends BaseWrapper<?>>[] value();

}
