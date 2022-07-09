
package com.zhisida.board.enums;

import com.zhisida.core.annotion.ExpEnumType;
import com.zhisida.core.consts.SysExpEnumConstant;
import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.core.factory.ExpEnumCodeFactory;

/**
 * 元事件分组
 *
 * @author Young-Pastor
 * @date 2022-06-20 11:52:21
 */
@ExpEnumType(module = SysExpEnumConstant.SYS_MODULE_EXP_CODE)
public enum BoardEventGroupExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 数据不存在
     */
    NOT_EXIST(1, "此数据不存在");

    private final Integer code;

    private final String message;
        BoardEventGroupExceptionEnum(Integer code, String message) {
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
