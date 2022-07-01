
package com.zhisida.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.mail.MailAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.email.MailSender;
import com.zhisida.core.email.modular.SimpleMailSender;
import com.zhisida.core.pojo.email.EmailConfigs;

/**
 * 邮件发送控制器
 *
 * @author Young-Pastor
 */
@Configuration
public class MailSenderConfig {

    /**
     * 邮件发射器
     *
     * @author Young-Pastor
     */
    @Bean
    public MailSender mailSender(SysConfigCache sysConfigCache) {
        EmailConfigs emailConfigs = sysConfigCache.getEmailConfigs();
        MailAccount mailAccount = new MailAccount();
        BeanUtil.copyProperties(emailConfigs, mailAccount);
        return new SimpleMailSender(mailAccount);
    }

}
