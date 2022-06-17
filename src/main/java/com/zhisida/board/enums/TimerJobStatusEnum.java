
package com.zhisida.board.enums;

import lombok.Getter;

/**
 * 定时任务的状态
 *
 * @author young-pastor
 */
@Getter
public enum TimerJobStatusEnum {

    /**
     * 启动状态
     */
    RUNNING(1),

    /**
     * 停止状态
     */
    STOP(2);

    private final Integer code;

    TimerJobStatusEnum(int code) {
        this.code = code;
    }

}
