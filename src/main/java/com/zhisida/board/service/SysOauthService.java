
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.zhyd.oauth.model.AuthCallback;
import com.zhisida.board.entity.SysOauthUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Oauth登录相关service接口
 *
 * @author Young-Pastor
 **/
public interface SysOauthService extends IService<SysOauthUser> {

    /**
     * 根据授权平台来源获取授权地址
     *
     * @param source 授权平台来源
     * @return 授权地址
     * @author Young-Pastor
     **/
    String getAuthorizeUrl(String source);

    /**
     * 授权后回调方法
     *
     * @param source   授权来源平台
     * @param callback 授权平台返回的用户信息
     * @param request  request请求
     * @return 登录成功的token
     * @author Young-Pastor
     **/
    String callback(String source, AuthCallback callback, HttpServletRequest request);
}
