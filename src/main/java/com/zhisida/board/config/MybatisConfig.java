
package com.zhisida.board.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zhisida.board.core.mybatis.dbid.BoardDatabaseIdProvider;
import com.zhisida.board.core.mybatis.fieldfill.CustomMetaObjectHandler;
import com.zhisida.board.core.mybatis.sqlfilter.DemoProfileSqlInterceptor;

/**
 * mybatis扩展插件配置
 *
 * @author Young-Pastor
 */
@Configuration
@MapperScan(basePackages = {"com.zhisida.board.**.mapper"})
public class MybatisConfig {

    /**
     * mybatis-plus分页插件
     *
     * @author Young-Pastor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 演示环境的sql拦截器
     * <p>
     * 演示环境只开放查询操作，其他都不允许
     *
     * @author Young-Pastor
     */
    @Bean
    public DemoProfileSqlInterceptor demoProfileSqlInterceptor() {
        return new DemoProfileSqlInterceptor();
    }

    /**
     * 自定义公共字段自动注入
     *
     * @author Young-Pastor
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }

    /**
     * 数据库id选择器
     *
     * @author Young-Pastor
     */
    @Bean
    public BoardDatabaseIdProvider boardDatabaseIdProvider() {
        return new BoardDatabaseIdProvider();
    }

}
