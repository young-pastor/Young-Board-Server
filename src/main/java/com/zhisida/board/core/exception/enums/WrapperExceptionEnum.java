
package com.zhisida.board.core.exception.enums;

import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.annotion.ExpEnumType;
import com.zhisida.board.core.consts.ExpEnumConstant;
import com.zhisida.board.core.factory.ExpEnumCodeFactory;

/**
 * 对象包装异常
 *
 * @author Young-Pastor
 */
@ExpEnumType(module = ExpEnumConstant.BOARD_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.WRAPPER_EXCEPTION_ENUM)
public enum WrapperExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 被包装的值不能是基本类型
     */
    BASIC_TYPE_ERROR(1, "被包装的值不能是基本类型"),

    /**
     * 获取转化字段的值异常
     */
    TRANSFER_FILED_VALUE_ERROR(2, "获取转化字段的值异常"),

    /**
     * 字段包装转化异常
     */
    TRANSFER_ERROR(3, "字段包装转化异常");

    private final Integer code;

    private final String message;

    WrapperExceptionEnum(Integer code, String message) {
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
