
package com.zhisida.system.enums;

import com.zhisida.core.annotion.ExpEnumType;
import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.core.factory.ExpEnumCodeFactory;
import com.zhisida.core.consts.GenExpEnumConstant;

/**
 * 代码生成基础配置相关异常枚举
 *
 * @author Young-Pastor
 */
@ExpEnumType(module = GenExpEnumConstant.GEN_CODE_EXCEPTION_ENUM, kind = GenExpEnumConstant.GEN_CONFIG_EXCEPTION_ENUM)
public enum CodeGenerateExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 代码生成基础配置不存在
     */
    CODE_GEN_NOT_EXIST(1, "代码生成基础配置不存在"),

    /**
     * 本地生成代码输出路径错误
     */
    CODE_GEN_NOT_PATH(2,"本地生成代码输出路径错误"),

    /**
     * 请检查此数据表中主键的定义
     */
    CODE_GEN_TABLE_NOT_PRI(3,"请检查此数据表中主键的定义");

    private final Integer code;

    private final String message;

    CodeGenerateExceptionEnum(Integer code, String message) {
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
