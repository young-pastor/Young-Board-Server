
package com.zhisida.board.core.tenant.exception;

import com.zhisida.board.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.board.core.exception.ServiceException;

/**
 * 多租户的异常
 *
 * @author young-pastor
 */
public class TenantException extends ServiceException {

    public TenantException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

}
