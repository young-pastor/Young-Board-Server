
package com.zhisida.board.enums;

import com.zhisida.board.core.annotion.ExpEnumType;
import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.factory.ExpEnumCodeFactory;
import com.zhisida.board.core.consts.SysExpEnumConstant;

/**
 * 字段关联配置
 *
 * @author young-pastor
 * @date 2022-06-20 11:45:42
 */
@ExpEnumType(module = SysExpEnumConstant.BOARD_SYS_MODULE_EXP_CODE)
public enum BoardTableConnectExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 数据不存在
     */
    NOT_EXIST(1, "此数据不存在");

    private final Integer code;

    private final String message;
        BoardTableConnectExceptionEnum(Integer code, String message) {
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
