
package com.zhisida.system.enums;

import lombok.Getter;

/**
 * 短信验证相关美剧
 *
 * @author Young-Pastor
 */
@Getter
public enum SmsVerifyEnum {

    /**
     * 验证成功
     */
    SUCCESS(10, "验证成功"),

    /**
     * 验证码错误
     */
    ERROR(20, "验证码错误"),

    /**
     * 验证码超时
     */
    EXPIRED(30, "验证码超时"),

    /**
     * 超过验证次数
     */
    TIMES_UP(40, "超过验证次数");

    private final Integer code;

    private final String message;

    SmsVerifyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
