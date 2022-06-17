
package com.zhisida.board.service;

import com.zhisida.board.enums.SmsSendStatusEnum;
import com.zhisida.board.enums.SmsVerifyEnum;
import com.zhisida.board.param.SysSmsSendParam;
import com.zhisida.board.param.SysSmsVerifyParam;

/**
 * 短信通知接口
 *
 * @author young-pastor
 */
public interface SysSmsSenderService {

    /**
     * 发送短信
     *
     * @param sysSmsSendParam 短信发送参数
     * @return 是否成功
     * @author young-pastor
     */
    boolean sendShortMessage(SysSmsSendParam sysSmsSendParam);

    /**
     * 验证短信
     *
     * @param sysSmsVerifyParam 短信验证参数
     * @return 校验结果
     * @author young-pastor
     */
    SmsVerifyEnum verifyShortMessage(SysSmsVerifyParam sysSmsVerifyParam);

    /**
     * 查看短信发送状态 0=未发送，1=发送成功，2=发送失败
     *
     * @param smsId 短信发送记录id
     * @return 发送状态
     * @author young-pastor
     */
    SmsSendStatusEnum getMessageSendStatus(Integer smsId);

}
