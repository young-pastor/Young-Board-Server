
package com.zhisida.board.enums;

import com.zhisida.board.core.annotion.ExpEnumType;
import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.factory.ExpEnumCodeFactory;
import com.zhisida.board.core.consts.SysExpEnumConstant;

/**
 * 系统应用相关异常枚举
 *
 * @author Young-Pastor
 */
@ExpEnumType(module = SysExpEnumConstant.BOARD_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_APP_EXCEPTION_ENUM)
public enum SysAppExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 应用不存在
     */
    APP_NOT_EXIST(1, "应用不存在"),

    /**
     * 应用编码重复
     */
    APP_CODE_REPEAT(2, "应用编码重复，请检查code参数"),

    /**
     * 应用名称重复
     */
    APP_NAME_REPEAT(3, "应用名称重复，请检查name参数"),

    /**
     * 默认激活的系统只能有一个
     */
    APP_ACTIVE_REPEAT(4, "默认激活的系统只能有一个，请检查active参数"),

    /**
     * 该应用下有菜单
     */
    APP_CANNOT_DELETE(5, "该应用下有菜单，无法删除");

    private final Integer code;

    private final String message;

    SysAppExceptionEnum(Integer code, String message) {
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
