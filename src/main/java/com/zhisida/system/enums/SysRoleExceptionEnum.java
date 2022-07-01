
package com.zhisida.system.enums;

import com.zhisida.core.annotion.ExpEnumType;
import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.core.factory.ExpEnumCodeFactory;
import com.zhisida.core.consts.SysExpEnumConstant;

/**
 * 系统角色相关异常枚举
 *
 * @author Young-Pastor
 */
@ExpEnumType(module = SysExpEnumConstant.BOARD_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_ROLE_EXCEPTION_ENUM)
public enum SysRoleExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 角色不存在
     */
    ROLE_NOT_EXIST(1, "角色不存在"),

    /**
     * 角色编码重复
     */
    ROLE_CODE_REPEAT(2, "角色编码重复，请检查code参数"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REPEAT(3, "角色名称重复，请检查name参数");

    private final Integer code;

    private final String message;

    SysRoleExceptionEnum(Integer code, String message) {
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
