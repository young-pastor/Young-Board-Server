
package com.zhisida.board.core.email.modular;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.zhisida.board.core.email.modular.exception.MailSendException;
import com.zhisida.board.core.email.modular.model.SendMailParam;
import com.zhisida.board.core.email.MailSender;

/**
 * 邮件发送器
 *
 * @author young-pastor
 */
public class SimpleMailSender implements MailSender {

    /**
     * 邮件配置
     */
    private final MailAccount mailAccount;

    public SimpleMailSender(MailAccount mailAccount) {
        this.mailAccount = mailAccount;
    }

    @Override
    public void sendMail(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(mailAccount, CollUtil.newArrayList(sendMailParam.getTo()), sendMailParam.getTitle(), sendMailParam.getContent(), false);
    }

    @Override
    public void sendMailHtml(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);

        //spring发送邮件
        MailUtil.send(mailAccount, CollUtil.newArrayList(sendMailParam.getTo()), sendMailParam.getTitle(), sendMailParam.getContent(), true);
    }

    /**
     * 校验发送邮件的请求参数
     *
     * @author young-pastor
     */
    private void assertSendMailParams(SendMailParam sendMailParam) {
        if (sendMailParam == null) {
            throw new MailSendException(400, "请求参数为空");
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTo())) {
            throw new MailSendException(400, "收件人邮箱为空");
        }

        if (ObjectUtil.isEmpty(sendMailParam.getTitle())) {
            throw new MailSendException(400, "邮件标题为空");
        }

        if (ObjectUtil.isEmpty(sendMailParam.getContent())) {
            throw new MailSendException(400, "邮件内容为空");
        }
    }

}
