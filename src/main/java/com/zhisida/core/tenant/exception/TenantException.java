
package com.zhisida.core.tenant.exception;

import com.zhisida.core.exception.enums.abs.AbstractBaseExceptionEnum;
import com.zhisida.core.exception.ServiceException;

/**
 * 多租户的异常
 *
 * @author Young-Pastor
 */
public class TenantException extends ServiceException {

    public TenantException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

}
