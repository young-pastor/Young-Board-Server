
package com.zhisida.system.param;

import com.zhisida.system.enums.SmsSendSourceEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 验证短信的参数
 *
 * @author Young-Pastor
 */
@Data
public class SysSmsVerifyParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phoneNumbers;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 模板号
     */
    @NotBlank(message = "模板号不能为空")
    private String templateCode;

    /**
     * 来源
     */
    private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

}
