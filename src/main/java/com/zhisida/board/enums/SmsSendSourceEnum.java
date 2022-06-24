
package com.zhisida.board.enums;

import lombok.Getter;

/**
 * 短信发送业务枚举
 *
 * @author Young-Pastor
 */
@Getter
public enum SmsSendSourceEnum {

    /**
     * APP
     */
    APP(1),

    /**
     * PC
     */
    PC(2),

    /**
     * OTHER
     */
    OTHER(3);

    private final Integer code;

    SmsSendSourceEnum(Integer code) {
        this.code = code;
    }
}
