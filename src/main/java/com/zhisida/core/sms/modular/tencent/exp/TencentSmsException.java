
package com.zhisida.core.sms.modular.tencent.exp;

import lombok.Getter;

/**
 * 短信发送异常
 *
 * @author Young-Pastor
 */
@Getter
public class TencentSmsException extends RuntimeException {

    private final String code;

    private final String errorMessage;

    public TencentSmsException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
