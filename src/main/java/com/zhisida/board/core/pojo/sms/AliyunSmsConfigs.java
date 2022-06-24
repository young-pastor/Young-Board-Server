
package com.zhisida.board.core.pojo.sms;

import lombok.Data;

/**
 * 阿里云oss相关配置
 *
 * @author Young-Pastor
 */
@Data
public class AliyunSmsConfigs {

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 签名名称
     */
    private String signName;

    /**
     * 登录验证码的模板
     */
    private String loginTemplateCode;

    /**
     * 短信失效时间（分钟）
     */
    private Integer invalidateMinutes = 2;

}
