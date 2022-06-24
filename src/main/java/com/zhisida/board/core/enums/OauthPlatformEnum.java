
package com.zhisida.board.core.enums;

import lombok.Getter;

/**
 * Oauth登录授权平台的枚举
 *
 * @author Young-Pastor
 **/
@Getter
public enum OauthPlatformEnum {

    /**
     * 码云
     */
    GITEE("gitee", "码云"),

    /**
     * github
     */
    GITHUB("github", "github");

    private final String code;

    private final String message;

    OauthPlatformEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
