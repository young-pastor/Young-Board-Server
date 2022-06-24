
package com.zhisida.board.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.core.sms.modular.aliyun.AliyunSmsSender;
import com.zhisida.board.core.sms.modular.aliyun.msign.impl.MapBasedMultiSignManager;
import com.zhisida.board.core.sms.modular.aliyun.prop.AliyunSmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.pojo.sms.AliyunSmsConfigs;
import com.zhisida.board.core.sms.SmsSender;

/**
 * 短信发送配置，短信发送的配置属性都在数据库的sys_config表中
 * <p>
 * 默认开启了阿里云的短信配置
 *
 * @author Young-Pastor
 */
@Configuration
public class SmsSenderConfig {

    /**
     * 短信发送器（阿里云）
     *
     * @author Young-Pastor
     */
    @Bean
    public SmsSender aliyunSmsSender(SysConfigCache sysConfigCache) {

        // 从数据库配置读取阿里云配置
        AliyunSmsConfigs aliyunSmsConfigs = sysConfigCache.getAliyunSmsConfigs();
        AliyunSmsProperties aliyunSmsProperties = new AliyunSmsProperties();
        BeanUtil.copyProperties(aliyunSmsConfigs, aliyunSmsProperties);

        return new AliyunSmsSender(new MapBasedMultiSignManager(), aliyunSmsProperties);
    }

}
