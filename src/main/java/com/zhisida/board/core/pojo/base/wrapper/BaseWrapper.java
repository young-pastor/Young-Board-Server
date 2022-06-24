
package com.zhisida.board.core.pojo.base.wrapper;

import java.util.Map;

/**
 * 基础包装接口，
 *
 * @author Young-Pastor
 */
public interface BaseWrapper<T> {

    /**
     * 具体包装的过程
     *
     * @param beWrappedModel 被包装的原始对象，可以是obj，list，page，PageResult
     * @return 包装后增加的增量集合
     * @author Young-Pastor
     */
    Map<String, Object> doWrap(T beWrappedModel);

}
