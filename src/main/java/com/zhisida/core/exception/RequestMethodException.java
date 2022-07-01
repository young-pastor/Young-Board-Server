
package com.zhisida.core.exception;

import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import lombok.Getter;

/**
 * 请求方法异常
 *
 * @author Young-Pastor
 */
@Getter
public class RequestMethodException extends RuntimeException {

    private final Integer code;

    private final String errorMessage;

    public RequestMethodException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }
}
