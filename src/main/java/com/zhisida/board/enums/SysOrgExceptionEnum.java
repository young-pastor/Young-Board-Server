
package com.zhisida.board.enums;

import com.zhisida.board.core.annotion.ExpEnumType;
import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.factory.ExpEnumCodeFactory;
import com.zhisida.board.core.consts.SysExpEnumConstant;

/**
 * 系统组织机构相关异常枚举
 *
 * @author Young-Pastor
 */
@ExpEnumType(module = SysExpEnumConstant.BOARD_SYS_MODULE_EXP_CODE, kind = SysExpEnumConstant.SYS_ORG_EXCEPTION_ENUM)
public enum SysOrgExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 组织机构不存在
     */
    ORG_NOT_EXIST(1, "组织机构不存在"),

    /**
     * 组织机构编码重复
     */
    ORG_CODE_REPEAT(2, "组织机构编码重复，请检查code参数"),

    /**
     * 组织机构名称重复
     */
    ORG_NAME_REPEAT(3, "组织机构名称重复，请检查name参数"),

    /**
     * 该机构下有员工
     */
    ORG_CANNOT_DELETE(4, "该机构或子机构下有员工，无法删除"),

    /**
     * 父节点不能和本节点一致，请重新选择父节点
     */
    ID_CANT_EQ_PID(5, "父节点不能和本节点一致，请重新选择父节点"),

    /**
     * 父节点不能为本节点的子节点，请重新选择父节点
     */
    PID_CANT_EQ_CHILD_ID(6, "父节点不能为本节点的子节点，请重新选择父节点");

    private final Integer code;

    private final String message;

    SysOrgExceptionEnum(Integer code, String message) {
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
