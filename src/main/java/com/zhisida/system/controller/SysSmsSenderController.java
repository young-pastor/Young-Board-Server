
package com.zhisida.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.annotion.Permission;
import com.zhisida.core.enums.LogAnnotionOpTypeEnum;
import com.zhisida.core.pojo.response.ResponseData;
import com.zhisida.core.pojo.response.SuccessResponseData;
import com.zhisida.system.enums.SmsVerifyEnum;
import com.zhisida.system.param.SysSmsInfoParam;
import com.zhisida.system.param.SysSmsSendParam;
import com.zhisida.system.param.SysSmsVerifyParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.zhisida.system.service.SysSmsSenderService;
import com.zhisida.system.service.SysSmsInfoService;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 短信发送控制器
 *
 * @author Young-Pastor
 */
@RestController
@RequestMapping
public class SysSmsSenderController {

    @Resource
    private SysSmsSenderService sysSmsSenderService;

    @Resource
    private SysSmsInfoService sysSmsInfoService;

    /**
     * 发送记录查询
     *
     * @author Young-Pastor
     */
    @Permission
    @GetMapping("/sms/page")
    @BusinessLog(title = "短信发送记录查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData page(SysSmsInfoParam sysSmsInfoParam) {
        return new SuccessResponseData(sysSmsInfoService.page(sysSmsInfoParam));
    }

    /**
     * 发送验证码短信
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sms/sendLoginMessage")
    @BusinessLog(title = "发送验证码短信")
    public ResponseData sendLoginMessage(@RequestBody @Validated SysSmsSendParam sysSmsSendParam) {

        // 设置模板中的参数
        HashMap<String, Object> paramMap = CollectionUtil.newHashMap();
        paramMap.put("code", RandomUtil.randomNumbers(6));
        sysSmsSendParam.setParams(paramMap);

        return new SuccessResponseData(sysSmsSenderService.sendShortMessage(sysSmsSendParam));
    }

    /**
     * 验证短信验证码
     *
     * @author Young-Pastor
     */
    @Permission
    @PostMapping("/sms/validateMessage")
    @BusinessLog(title = "验证短信验证码")
    public ResponseData validateMessage(@RequestBody @Validated SysSmsVerifyParam sysSmsVerifyParam) {
        SmsVerifyEnum smsVerifyEnum = sysSmsSenderService.verifyShortMessage(sysSmsVerifyParam);
        return new SuccessResponseData(smsVerifyEnum);
    }

}
