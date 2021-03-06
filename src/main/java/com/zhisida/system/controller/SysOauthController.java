
package com.zhisida.system.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.core.consts.CommonConstant;
import com.zhisida.core.consts.SymbolConstant;
import com.zhisida.system.cache.SysConfigCache;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.zhisida.system.service.SysOauthService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Oauth登录控制器
 *
 * @author Young-Pastor
 **/
@RestController
public class SysOauthController {

    @Resource
    private SysOauthService sysOauthService;

    /**
     * oauth登录
     *
     * @author Young-Pastor
     **/
    @GetMapping("/oauth/render/{source}")
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        String authorizeUrl = sysOauthService.getAuthorizeUrl(source);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * oauth平台中配置的授权回调地址
     *
     * @author Young-Pastor
     **/
    @GetMapping("/oauth/callback/{source}")
    public void callback(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = sysOauthService.callback(source, callback, request);
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        String webUrl = sysConfigCache.getWebUrl();
        response.sendRedirect(webUrl + SymbolConstant.QUESTION_MARK + CommonConstant.TOKEN_NAME + SymbolConstant.EQUAL + token);
    }
}
