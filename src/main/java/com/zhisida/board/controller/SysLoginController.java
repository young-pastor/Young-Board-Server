
package com.zhisida.board.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.spring.SpringUtil;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.context.login.LoginContextHolder;
import com.zhisida.board.core.exception.AuthException;
import com.zhisida.board.core.exception.enums.AuthExceptionEnum;
import com.zhisida.board.core.pojo.response.ResponseData;
import com.zhisida.board.core.pojo.response.SuccessResponseData;
import com.zhisida.board.service.SysAuthService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录控制器
 *
 * @author Young-Pastor
 */
@RestController
public class SysLoginController {

    @Resource
    private SysAuthService sysAuthService;

    @Lazy
    @Resource
    private CaptchaService captchaService;

    @Resource
    private SysConfigCache sysConfigCache;

    /**
     * 获取是否开启租户的标识
     *
     * @author Young-Pastor
     */
    @GetMapping("/getTenantOpen")
    public ResponseData getTenantOpen() {
        return new SuccessResponseData(sysConfigCache.getTenantOpenFlag());
    }

    /**
     * 账号密码登录
     *
     * @author Young-Pastor
     */
    @PostMapping("/login")
    public ResponseData login(@RequestBody Dict dict) {
        String account = dict.getStr("account");
        String password = dict.getStr("password");
        String tenantCode = dict.getStr("tenantCode");

        //检测是否开启验证码
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCaptchaOpenFlag()) {
            verificationCode(dict.getStr("code"));
        }

        //如果系统开启了多租户开关，则添加租户的临时缓存
        if (sysConfigCache.getTenantOpenFlag()) {
            sysAuthService.cacheTenantInfo(tenantCode);
        }

        String token = sysAuthService.login(account, password);
        return new SuccessResponseData(token);
    }

    /**
     * 退出登录
     *
     * @author Young-Pastor
     */
    @GetMapping("/logout")
    public void logout() {
        sysAuthService.logout();
    }

    /**
     * 获取当前登录用户信息
     *
     * @author Young-Pastor
     */
    @GetMapping("/getLoginUser")
    public ResponseData getLoginUser() {
        return new SuccessResponseData(LoginContextHolder.me().getSysLoginUserUpToDate());
    }

    /**
     * 获取验证码开关
     *
     * @author Young-Pastor
     */
    @GetMapping("/getCaptchaOpen")
    public ResponseData getCaptchaOpen() {
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        return new SuccessResponseData(sysConfigCache.getCaptchaOpenFlag());
    }

    /**
     * 校验验证码
     *
     * @author Young-Pastor
     */
    private boolean verificationCode(String code) {
        CaptchaVO vo = new CaptchaVO();
        vo.setCaptchaVerification(code);
        if (!captchaService.verification(vo).isSuccess()) {
            throw new AuthException(AuthExceptionEnum.CONSTANT_EMPTY_ERROR);
        }
        return true;
    }

}
