
package com.zhisida.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.exception.ServiceException;
import com.zhisida.core.pojo.oauth.OauthConfigs;
import com.zhisida.system.cache.SysOauthCache;
import com.zhisida.core.enums.OauthPlatformEnum;
import com.zhisida.system.enums.SysOauthExceptionEnum;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhisida.system.service.SysAuthService;
import com.zhisida.system.entity.SysOauthUser;
import com.zhisida.system.mapper.SysOauthMapper;
import com.zhisida.system.service.SysOauthService;
import com.zhisida.system.entity.SysUser;
import com.zhisida.system.service.SysUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Oauth登录相关service接口实现类
 *
 * @author Young-Pastor
 **/
@Service
public class SysOauthServiceImpl extends ServiceImpl<SysOauthMapper, SysOauthUser> implements SysOauthService {

    @Resource
    private SysOauthCache oauthCache;

    @Resource
    private SysAuthService sysAuthService;

    @Resource
    private SysUserService sysUserService;


    @Override
    public String getAuthorizeUrl(String source) {
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        Boolean enableOauthLogin = sysConfigCache.getEnableOauthLogin();
        if (!enableOauthLogin) {
            throw new ServiceException(SysOauthExceptionEnum.OAUTH_DISABLED);
        }
        AuthRequest authRequest = this.getAuthRequest(source);
        return authRequest.authorize(AuthStateUtils.createState());
    }

    @SuppressWarnings("all")
    @Override
    public String callback(String source, AuthCallback callback, HttpServletRequest request) {
        AuthRequest authRequest = this.getAuthRequest(source);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        if (response.ok()) {
            AuthUser authUser = response.getData();
            return doLogin(authUser);
        } else {
            throw new ServiceException(response.getCode(), response.getMsg());
        }
    }

    /**
     * 根据用户授权信息进行登录
     *
     * @param authUser 用户授权信息
     * @return token
     * @author Young-Pastor
     **/
    @Transactional(rollbackFor = Exception.class)
    public String doLogin(AuthUser authUser) {
        //获取uuid
        String uuid = authUser.getUuid();
        LambdaQueryWrapper<SysOauthUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOauthUser::getUuid, uuid);
        SysOauthUser oauthUser = this.getOne(queryWrapper);
        //从没授权登录过
        if (ObjectUtil.isNull(oauthUser)) {
            //将授权的用户信息保存到sys_oauth_user表和sys_user表
            this.saveByAuthUser(authUser);
            //再获取oauthUser用户
            oauthUser = this.getOne(queryWrapper);
        }
        //获取用户账户信息进行登录
        Long userId = oauthUser.getId();
        SysUser sysUser = sysUserService.getUserById(userId);
        return sysAuthService.doLogin(sysUser);
    }

    /**
     * 将授权的用户信息保存到sys_oauth_user表和sys_user表
     *
     * @param authUser 用户授权信息
     * @return void
     * @author Young-Pastor
     **/
    @Transactional(rollbackFor = Exception.class)
    public void saveByAuthUser(AuthUser authUser) {
        //生成用户id
        long userId = IdWorker.getId();
        //创建oauthUser对象
        SysOauthUser oauthUser = new SysOauthUser();
        oauthUser.setId(userId);
        this.fillOauthUserInfo(oauthUser, authUser);
        //创建sysUser对象
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        //将授权的用户信息保存到user表
        sysUserService.saveAuthUserToUser(authUser, sysUser);
        this.save(oauthUser);
    }

    /**
     * 根据具体的授权来源，获取授权请求
     *
     * @param source 授权平台来源
     * @return 授权请求
     * @author Young-Pastor
     **/
    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest;
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (source.toLowerCase().equals(OauthPlatformEnum.GITEE.getCode())) {
            OauthConfigs giteeOauthConfigs = sysConfigCache.getGiteeOauthConfigs();
            authRequest = new AuthGiteeRequest(AuthConfig.builder()
                    .clientId(giteeOauthConfigs.getClientId())
                    .clientSecret(giteeOauthConfigs.getClientSecret())
                    .redirectUri(giteeOauthConfigs.getRedirectUri())
                    .build(), oauthCache);
        } else if (source.toLowerCase().equals(OauthPlatformEnum.GITHUB.getCode())) {
            OauthConfigs githubOauthConfigs = sysConfigCache.getGithubOauthConfigs();
            authRequest = new AuthGithubRequest(AuthConfig.builder()
                    .clientId(githubOauthConfigs.getClientId())
                    .clientSecret(githubOauthConfigs.getClientSecret())
                    .redirectUri(githubOauthConfigs.getRedirectUri())
                    .build(), oauthCache);
        } else {
            throw new ServiceException(SysOauthExceptionEnum.OAUTH_NOT_SUPPORT);
        }
        return authRequest;
    }

    /**
     * 将授权用户信息填充到oauthUser
     *
     * @param oauthUser 系统授权用户信息
     * @param authUser  平台授权用户信息
     * @return void
     * @author Young-Pastor
     **/
    private void fillOauthUserInfo(SysOauthUser oauthUser, AuthUser authUser) {
        oauthUser.setUuid(authUser.getUuid());
        oauthUser.setAccessToken(authUser.getToken().getAccessToken());
        oauthUser.setNickName(authUser.getNickname());
        oauthUser.setAvatar(authUser.getAvatar());
        oauthUser.setBlog(authUser.getBlog());
        oauthUser.setCompany(authUser.getCompany());
        oauthUser.setLocation(authUser.getLocation());
        oauthUser.setEmail(authUser.getEmail());
        oauthUser.setSource(authUser.getSource());
        oauthUser.setRemark(authUser.getRemark());
        if (ObjectUtil.isNotNull(authUser.getGender())) {
            oauthUser.setGender(authUser.getGender().getDesc());
        }
    }
}
