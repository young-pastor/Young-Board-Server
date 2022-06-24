
package com.zhisida.board.core.sms.modular.aliyun.exp;

import lombok.Getter;

/**
 * 短信发送异常
 *
 * @author Young-Pastor
 */
@Getter
public class AliyunSmsException extends RuntimeException {

    private final String code;

    private final String errorMessage;

    public AliyunSmsException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
