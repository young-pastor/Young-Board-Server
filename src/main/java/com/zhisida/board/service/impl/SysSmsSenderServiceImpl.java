
package com.zhisida.board.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import com.zhisida.board.core.consts.CommonConstant;
import com.zhisida.board.core.sms.SmsSender;
import com.zhisida.board.enums.SmsSendStatusEnum;
import com.zhisida.board.enums.SmsTypeEnum;
import com.zhisida.board.enums.SmsVerifyEnum;
import com.zhisida.board.param.SysSmsSendParam;
import com.zhisida.board.param.SysSmsVerifyParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.zhisida.board.service.SysSmsSenderService;
import com.zhisida.board.service.SysSmsInfoService;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 短信发送接口实现类
 *
 * @author Young-Pastor
 */
@Component
public class SysSmsSenderServiceImpl implements SysSmsSenderService {

    private static final Log log = Log.get();

    @Resource
    private SmsSender smsSender;

    @Resource
    private SysSmsInfoService sysSmsInfoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendShortMessage(SysSmsSendParam sysSmsSendParam) {

        Map<String, Object> params = sysSmsSendParam.getParams();
        log.info(">>> 发送短信Provider接口，参数为：" + JSON.toJSONString(sysSmsSendParam));

        //如果是纯消息发送，直接发送
        if (SmsTypeEnum.MESSAGE.equals(sysSmsSendParam.getSmsTypeEnum())) {
            smsSender.sendSms(sysSmsSendParam.getPhoneNumbers(), sysSmsSendParam.getTemplateCode(), params);
            log.info(">>> 发送短信Provider接口--message，params的map具体为：" + JSON.toJSONString(params));
        } else {

            //如果是验证类消息发送，需要存储到数据库验证码信息
            String validateCode;

            //如果传的参数中没有code，就初始化一个code放到参数map里
            if (params != null && params.get(CommonConstant.CODE) != null) {
                validateCode = params.get(CommonConstant.CODE).toString();
            } else {
                validateCode = RandomUtil.randomNumbers(6);
                if (params == null) {
                    params = CollectionUtil.newHashMap();
                }
                params.put(CommonConstant.CODE, validateCode);
            }

            log.info(">>> 发送短信Provider接口，params的map具体为：" + JSON.toJSONString(params));
            log.info(">>> 发送短信Provider接口，验证码为：" + validateCode);

            //存储短信到数据库
            Long smsId = sysSmsInfoService.saveSmsInfo(sysSmsSendParam, validateCode);

            log.info(">>> 开始发送短信：发送的电话号码= " + sysSmsSendParam.getPhoneNumbers() + ",发送的模板号=" + sysSmsSendParam.getTemplateCode() + "，发送的参数是：" + JSON.toJSONString(params));

            //发送短信
            smsSender.sendSms(sysSmsSendParam.getPhoneNumbers(), sysSmsSendParam.getTemplateCode(), params);

            //更新短信发送状态
            sysSmsInfoService.updateSmsInfo(smsId, SmsSendStatusEnum.SUCCESS);
        }

        return true;
    }

    @Override
    public SmsVerifyEnum verifyShortMessage(SysSmsVerifyParam sysSmsVerifyParam) {
        log.info(">>> 验证短信Provider接口，参数为：" + JSON.toJSONString(sysSmsVerifyParam));
        SmsVerifyEnum smsVerifyEnum = sysSmsInfoService.validateSmsInfo(sysSmsVerifyParam);
        log.info(">>> 验证短信Provider接口，响应结果为：" + JSON.toJSONString(smsVerifyEnum));
        return smsVerifyEnum;
    }

    @Override
    public SmsSendStatusEnum getMessageSendStatus(Integer smsId) {
        return null;
    }

}
