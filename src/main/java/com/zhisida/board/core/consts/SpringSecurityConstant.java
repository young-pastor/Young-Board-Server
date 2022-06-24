
package com.zhisida.board.core.consts;

/**
 * SpringSecurity相关常量
 *
 * @author Young-Pastor
 */
public interface SpringSecurityConstant {

    /**
     * 放开权限校验的接口
     */
    String[] NONE_SECURITY_URL_PATTERNS = {

            //前端的
            "/favicon.ico",

            //swagger相关的
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/configuration/ui",
            "/configuration/security",

            //后端的
            "/",
            "/login",
            "/logout",
            "/oauth/**",

            //文件的
            "/sysFileInfo/upload",
            "/sysFileInfo/download",
            "/sysFileInfo/preview",

            //druid的
            "/druid/**",

            //获取验证码
            "/captcha/**",
            "/getCaptchaOpen",

    };

}
