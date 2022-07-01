
package com.zhisida.config;

import com.zhisida.core.aop.BusinessLogAop;
import com.zhisida.core.aop.DataScopeAop;
import com.zhisida.core.aop.PermissionAop;
import com.zhisida.core.aop.WrapperAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 切面配置
 *
 * @author Young-Pastor
 */
@Configuration
public class AopConfig {

    /**
     * 日志切面
     *
     * @author Young-Pastor
     */
    @Bean
    public BusinessLogAop businessLogAop() {
        return new BusinessLogAop();
    }

    /**
     * 权限切面
     *
     * @author Young-Pastor
     */
    @Bean
    public PermissionAop permissionAop() {
        return new PermissionAop();
    }

    /**
     * 数据范围切面
     *
     * @author Young-Pastor
     */
    @Bean
    public DataScopeAop dataScopeAop() {
        return new DataScopeAop();
    }

    /**
     * 结果包装的aop
     *
     * @author Young-Pastor
     */
    @Bean
    public WrapperAop wrapperAop() {
        return new WrapperAop();
    }
}
