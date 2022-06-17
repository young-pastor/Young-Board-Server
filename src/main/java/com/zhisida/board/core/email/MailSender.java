
package com.zhisida.board.core.email;


import com.zhisida.board.core.email.modular.model.SendMailParam;

/**
 * 邮件收发统一接口
 *
 * @author young-pastor
 */
public interface MailSender {

    /**
     * 发送普通邮件
     *
     * @author young-pastor
     */
    void sendMail(SendMailParam sendMailParam);

    /**
     * 发送html的邮件
     *
     * @author young-pastor
     */
    void sendMailHtml(SendMailParam sendMailParam);

}
