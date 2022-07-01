
package com.zhisida.core.email;


import com.zhisida.core.email.modular.model.SendMailParam;

/**
 * 邮件收发统一接口
 *
 * @author Young-Pastor
 */
public interface MailSender {

    /**
     * 发送普通邮件
     *
     * @author Young-Pastor
     */
    void sendMail(SendMailParam sendMailParam);

    /**
     * 发送html的邮件
     *
     * @author Young-Pastor
     */
    void sendMailHtml(SendMailParam sendMailParam);

}
