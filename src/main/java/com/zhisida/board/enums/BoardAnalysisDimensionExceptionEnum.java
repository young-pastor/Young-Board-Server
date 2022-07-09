package com.zhisida.board.enums;

import com.zhisida.core.annotion.ExpEnumType;
import com.zhisida.core.consts.SysExpEnumConstant;
import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.core.factory.ExpEnumCodeFactory;

/**
 * 实时分析分组属性
 *
 * @author Young-Pastor
 * @date 2022-07-08 14:40:03
 */
@ExpEnumType(module = SysExpEnumConstant.SYS_MODULE_EXP_CODE)
public enum BoardAnalysisDimensionExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 数据不存在
     */
    NOT_EXIST(1, "此数据不存在");

    private final Integer code;

    private final String message;
        BoardAnalysisDimensionExceptionEnum(Integer code, String message) {
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
