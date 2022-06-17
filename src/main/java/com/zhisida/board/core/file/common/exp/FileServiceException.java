
package com.zhisida.board.core.file.common.exp;

import lombok.Getter;

/**
 * 文件操作业务异常
 *
 * @author young-pastor
 */
@Getter
public class FileServiceException extends RuntimeException {

    public FileServiceException(String message) {
        super(message);
    }

}
