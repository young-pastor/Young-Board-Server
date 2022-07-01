
package com.zhisida.core.email.modular.exception;

import lombok.Getter;

/**
 * 邮件发送异常
 *
 * @author Young-Pastor
 */
@Getter
public class MailSendException extends RuntimeException {

    private final Integer code;

    private final String errorMessage;

    public MailSendException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

}
