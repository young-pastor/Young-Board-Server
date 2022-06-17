
package com.zhisida.board.core.pojo.email;

import lombok.Data;

/**
 * 邮件的配置
 *
 * @author young-pastor
 */
@Data
public class EmailConfigs {

    /**
     * host
     */
    private String host;

    /**
     * 邮箱用户名
     */
    private String user;

    /**
     * 邮箱密码或者安全码
     */
    private String pass;

    /**
     * 邮箱端口
     */
    private Integer port;

    /**
     * 邮箱发件人
     */
    private String from;

    /**
     * 使用 SSL安全连接
     */
    private Boolean sslEnable;

}
