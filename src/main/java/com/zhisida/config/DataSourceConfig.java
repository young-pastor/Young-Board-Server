
package com.zhisida.config;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.pojo.druid.DruidProperties;

import java.util.HashMap;

/**
 * Druid配置
 *
 * @author Young-Pastor
 */
@Configuration
public class DataSourceConfig {

    /**
     * druid属性配置
     *
     * @author Young-Pastor
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidProperties druidProperties() {
        return new DruidProperties();
    }

    /**
     * druid数据库连接池
     *
     * @author Young-Pastor
     */
    @Bean(initMethod = "init")
    public DruidDataSource dataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * druid监控，配置StatViewServlet
     *
     * @author Young-Pastor
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServletRegistration(SysConfigCache sysConfigCache) {

        // 设置servlet的参数
        HashMap<String, String> statViewServletParams = CollectionUtil.newHashMap();
        statViewServletParams.put("resetEnable", "true");
        ServletRegistrationBean<StatViewServlet> registration = new ServletRegistrationBean<>(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        statViewServletParams.put("loginUsername", sysConfigCache.getDruidLoginConfigs().getLoginUsername());
        statViewServletParams.put("loginPassword", sysConfigCache.getDruidLoginConfigs().getLoginPassword());
        registration.setInitParameters(statViewServletParams);
        return registration;
    }

}
