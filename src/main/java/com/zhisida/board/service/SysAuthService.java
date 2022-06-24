
package com.zhisida.board.service;

import com.zhisida.board.core.pojo.login.SysLoginUser;
import com.zhisida.board.entity.SysUser;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证相关service
 *
 * @author Young-Pastor
 */
public interface SysAuthService {

    /**
     * 账号密码登录
     *
     * @param account  账号
     * @param password 密码
     * @return token
     * @author Young-Pastor
     */
    String login(String account, String password);

    /**
     * 根据已有用户信息登录
     *
     * @param sysUser 用户信息
     * @return token
     * @author Young-Pastor
     **/
    String doLogin(SysUser sysUser);

    /**
     * 从request获取token
     *
     * @param request request
     * @return token
     * @author Young-Pastor
     */
    String getTokenFromRequest(HttpServletRequest request);

    /**
     * 根据token获取登录用户信息
     *
     * @param token token
     * @return 当前登陆的用户信息
     * @author Young-Pastor
     */
    SysLoginUser getLoginUserByToken(String token);

    /**
     * 退出登录
     *
     * @author Young-Pastor
     */
    void logout();

    /**
     * 设置SpringSecurityContext上下文，方便获取用户
     *
     * @param sysLoginUser 当前登录用户信息
     * @author Young-Pastor
     */
    void setSpringSecurityContextAuthentication(SysLoginUser sysLoginUser);

    /**
     * 获取SpringSecurityContext中认证信息
     *
     * @return 认证信息
     * @author Young-Pastor
     */
    Authentication getAuthentication();

    /**
     * 校验token是否正确
     *
     * @param token token
     * @author Young-Pastor
     */
    void checkToken(String token);

    /**
     * 临时缓存租户信息
     *
     * @param tenantCode 多租户编码
     * @author Young-Pastor
     */
    void cacheTenantInfo(String tenantCode);

    /**
     * 根据系统用户构造用户登陆信息
     *
     * @param sysUser 系统用户
     * @return 用户信息
     * @author Young-Pastor
     **/
    SysLoginUser genSysLoginUser(SysUser sysUser);

    /**
     * 新增用户的数据授权范围
     *
     * @author Young-Pastor
     */
    void refreshUserDataScope(Long orgId);

}
