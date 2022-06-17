
package com.zhisida.board.config;

import com.zhisida.board.core.aop.BusinessLogAop;
import com.zhisida.board.core.aop.DataScopeAop;
import com.zhisida.board.core.aop.PermissionAop;
import com.zhisida.board.core.aop.WrapperAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 切面配置
 *
 * @author young-pastor
 */
@Configuration
public class AopConfig {

    /**
     * 日志切面
     *
     * @author young-pastor
     */
    @Bean
    public BusinessLogAop businessLogAop() {
        return new BusinessLogAop();
    }

    /**
     * 权限切面
     *
     * @author young-pastor
     */
    @Bean
    public PermissionAop permissionAop() {
        return new PermissionAop();
    }

    /**
     * 数据范围切面
     *
     * @author young-pastor
     */
    @Bean
    public DataScopeAop dataScopeAop() {
        return new DataScopeAop();
    }

    /**
     * 结果包装的aop
     *
     * @author young-pastor
     */
    @Bean
    public WrapperAop wrapperAop() {
        return new WrapperAop();
    }
}
