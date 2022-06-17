
package com.zhisida.board.enums;

import com.zhisida.board.core.consts.GenExpEnumConstant;
import com.zhisida.board.core.annotion.ExpEnumType;
import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.factory.ExpEnumCodeFactory;

/**
 * 代码生成详细配置
 *
 * @author young-pastor
 */
@ExpEnumType(module = GenExpEnumConstant.BOARD_GEN_MODULE_EXP_CODE, kind = GenExpEnumConstant.GEN_CONFIG_EXCEPTION_ENUM)
public enum SysCodeGenerateConfigExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 数据不存在
     */
    NOT_EXIST(1, "此数据不存在");

    private final Integer code;

    private final String message;
        SysCodeGenerateConfigExceptionEnum(Integer code, String message) {
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
