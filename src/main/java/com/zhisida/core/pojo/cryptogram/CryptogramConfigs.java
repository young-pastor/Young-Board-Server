
package com.zhisida.core.pojo.cryptogram;

import lombok.Data;

/**
 * 框架中加解密配置
 *
 * @author Young-Pastor
 **/
@Data
public class CryptogramConfigs {

    /**
     * token是否加解密
     */
    private Boolean tokenEncDec;

    /**
     * 操作日志是否加密
     */
    private Boolean opLogEnc;

    /**
     * 登录登出日志是否加密
     */
    private Boolean visLogEnc;

    /**
     * 铭感字段值是否加解密
     */
    private Boolean fieldEncDec;

}
