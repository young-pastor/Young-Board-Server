
package com.zhisida.board.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhisida.board.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Oauth登录用户表
 *
 * @author Young-Pastor
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_oauth_user")
public class SysOauthUser extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 第三方平台的用户唯一id
     */
    private String uuid;

    /**
     * 用户授权的token
     */
    private String accessToken;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户网址
     */
    private String blog;

    /**
     * 所在公司
     */
    private String company;

    /**
     * 位置
     */
    private String location;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户来源
     */
    private String source;

    /**
     * 用户备注（各平台中的用户个人介绍）
     */
    private String remark;
}
