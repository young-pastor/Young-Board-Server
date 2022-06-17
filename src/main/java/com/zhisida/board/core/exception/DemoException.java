
package com.zhisida.board.core.exception;

import lombok.Getter;

/**
 * 演示环境，无法操作的异常
 *
 * @author young-pastor
 */
@Getter
public class DemoException extends ServiceException {

    private static final int DEMO_EXP_CODE = 14000;

    private static final String DEMO_EXP_ERROR_MESSAGE = "演示环境，无法操作！";

    public DemoException() {
        super(DEMO_EXP_CODE, DEMO_EXP_ERROR_MESSAGE);
    }

}
