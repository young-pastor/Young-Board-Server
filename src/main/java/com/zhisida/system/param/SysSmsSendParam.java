
package com.zhisida.system.param;

import com.zhisida.system.enums.SmsSendSourceEnum;
import lombok.Data;
import com.zhisida.system.enums.SmsTypeEnum;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 发送短信的参数
 *
 * @author Young-Pastor
 */
@Data
public class SysSmsSendParam {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号码为空，请检查phoneNumbers参数")
    private String phoneNumbers;

    /**
     * 模板号
     */
    @NotBlank(message = "模板号为空，请检查templateCode参数")
    private String templateCode;

    /**
     * 模板中的参数
     */
    private Map<String, Object> params;

    /**
     * 发送源
     */
    private SmsSendSourceEnum smsSendSourceEnum = SmsSendSourceEnum.PC;

    /**
     * 消息类型，1验证码，2消息，默认不传为验证码
     */
    private SmsTypeEnum smsTypeEnum = SmsTypeEnum.SMS;

}
