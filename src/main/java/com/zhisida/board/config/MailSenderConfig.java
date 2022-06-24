
package com.zhisida.board.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.email.MailSender;
import com.zhisida.board.core.email.modular.SimpleMailSender;
import com.zhisida.board.core.pojo.email.EmailConfigs;

/**
 * 邮件发送控制器
 *
 * @author young-pastor
 */
@Configuration
public class MailSenderConfig {

    /**
     * 邮件发射器
     *
     * @author young-pastor
     */
    @Bean
    public MailSender mailSender(SysConfigCache sysConfigCache) {
        EmailConfigs emailConfigs = sysConfigCache.getEmailConfigs();
        MailAccount mailAccount = new MailAccount();
        BeanUtil.copyProperties(emailConfigs, mailAccount);
        return new SimpleMailSender(mailAccount);
    }

}
