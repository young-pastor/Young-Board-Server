
package com.zhisida.board.core.exception.enums.abs;

/**
 * 异常枚举格式规范
 *
 * @author young-pastor
 */
public interface AbstractBaseExceptionEnum {

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     * @author young-pastor
     */
    Integer getCode();

    /**
     * 获取异常的提示信息
     *
     * @return 提示信息
     * @author young-pastor
     */
    String getMessage();

}
