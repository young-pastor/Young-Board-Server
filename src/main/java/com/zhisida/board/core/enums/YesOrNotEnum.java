
package com.zhisida.board.core.enums;

import lombok.Getter;

/**
 * 是或否的枚举
 *
 * @author Young-Pastor
 */
@Getter
public enum YesOrNotEnum {

    /**
     * 是
     */
    Y("Y", "是"),

    /**
     * 否
     */
    N("N", "否");

    private final String code;

    private final String message;

    YesOrNotEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
