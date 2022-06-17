
package com.zhisida.board.core.enums;

import lombok.Getter;

/**
 * 菜单权重枚举
 *
 * @author young-pastor
 */
@Getter
public enum MenuWeightEnum {

    /**
     * 系统权重
     */
    SUPER_ADMIN_WEIGHT(1, "系统权重"),

    /**
     * 业务权重
     */
    DEFAULT_WEIGHT(2, "业务权重");

    private final Integer code;

    private final String name;

    MenuWeightEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
