
package com.zhisida.board.core.enums;

import lombok.Getter;

/**
 * 通知公告用户状态
 *
 * @author Young-Pastor
 */
@Getter
public enum NoticeUserStatusEnum {

    /**
     * 未读
     */
    UNREAD(0, "未读"),

    /**
     * 已读
     */
    READ(1, "已读");

    private final Integer code;

    private final String message;

    NoticeUserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
