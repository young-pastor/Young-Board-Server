
package com.zhisida.core.context.system;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 使用system模块相关功能的接口
 *
 * @author Young-Pastor
 */
public class SystemContextHolder {

    public static SystemContext me() {
        return SpringUtil.getBean(SystemContext.class);
    }

}
