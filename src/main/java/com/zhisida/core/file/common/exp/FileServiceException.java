
package com.zhisida.core.file.common.exp;

import lombok.Getter;

/**
 * 文件操作业务异常
 *
 * @author Young-Pastor
 */
@Getter
public class FileServiceException extends RuntimeException {

    public FileServiceException(String message) {
        super(message);
    }

}
