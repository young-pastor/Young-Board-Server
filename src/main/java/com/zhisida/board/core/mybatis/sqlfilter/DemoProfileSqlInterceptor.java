
package com.zhisida.board.core.mybatis.sqlfilter;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.zhisida.board.core.consts.SpringSecurityConstant;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.exception.DemoException;
import com.zhisida.board.core.util.HttpServletUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.AntPathMatcher;

import java.sql.Connection;

/**
 * 演示环境的sql过滤器，只放开select语句，其他语句都不放过
 *
 * @author Young-Pastor
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DemoProfileSqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

//        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
//        // 演示环境没开，直接跳过此过滤器
//        if (!sysConfigCache.getDemoEnvFlag()) {
        if(true){
            return invocation.proceed();
        }

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        } else {

            //放开不进行安全过滤的接口
            for (String notAuthResource : SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                if (antPathMatcher.match(notAuthResource, HttpServletUtil.getRequest().getRequestURI())) {
                    return invocation.proceed();
                }
            }
            throw new DemoException();
        }
    }

}
