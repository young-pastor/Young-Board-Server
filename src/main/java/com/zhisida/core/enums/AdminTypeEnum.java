
package com.zhisida.core.enums;

import lombok.Getter;

/**
 * 管理员类型枚举
 *
 * @author Young-Pastor
 */
@Getter
public enum AdminTypeEnum {

    /**
     * 超级管理员
     */
    SUPER_ADMIN(1, "超级管理员"),

    /**
     * 非管理员
     */
    NONE(2, "非管理员");

    private final Integer code;

    private final String message;

    AdminTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
