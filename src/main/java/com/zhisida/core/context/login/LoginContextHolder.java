
package com.zhisida.core.context.login;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 当前登录用户信息获取的接口
 *
 * @author Young-Pastor
 */
public class LoginContextHolder {

    public static LoginContext me() {
        return SpringUtil.getBean(LoginContext.class);
    }

}
