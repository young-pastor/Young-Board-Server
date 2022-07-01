
package com.zhisida.core.exception.enums;

import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.core.annotion.ExpEnumType;
import com.zhisida.core.consts.ExpEnumConstant;
import com.zhisida.core.factory.ExpEnumCodeFactory;

/**
 * 请求方法相关异常枚举
 *
 * @author Young-Pastor
 */
@ExpEnumType(module = ExpEnumConstant.BOARD_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.REQUEST_METHOD_EXCEPTION_ENUM)
public enum RequestMethodExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 不支持该请求方法，请求方法应为POST
     */
    REQUEST_METHOD_IS_POST(1, "不支持该请求方法，请求方法应为POST"),

    /**
     * 不支持该请求方法，请求方法应为GET
     */
    REQUEST_METHOD_IS_GET(2, "不支持该请求方法，请求方法应为GET");

    private final Integer code;

    private final String message;

    RequestMethodExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
