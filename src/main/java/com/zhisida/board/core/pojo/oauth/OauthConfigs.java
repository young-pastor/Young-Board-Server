
package com.zhisida.board.core.pojo.oauth;

import lombok.Data;

/**
 * Oauth第三方登录配置
 *
 * @author Young-Pastor
 **/
@Data
public class OauthConfigs {

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;

    /**
     * 回调地址
     */
    private String redirectUri;
}
