
package com.zhisida.board.core.email.modular.model;

import lombok.Data;

/**
 * 发送邮件的请求参数
 *
 * @author Young-Pastor
 */
@Data
public class SendMailParam {

    /**
     * 发送给某人的邮箱
     */
    private String to;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;
}
