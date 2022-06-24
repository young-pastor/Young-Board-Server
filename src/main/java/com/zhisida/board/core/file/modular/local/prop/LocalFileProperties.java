
package com.zhisida.board.core.file.modular.local.prop;

import lombok.Data;

/**
 * 本地文件存储配置
 *
 * @author Young-Pastor
 */
@Data
public class LocalFileProperties {

    /**
     * 本地文件存储位置（linux）
     */
    private String localFileSavePathLinux = "/tmp/tempFilePath";

    /**
     * 本地文件存储位置（windows）
     */
    private String localFileSavePathWin = "D:\\tempFilePath";

}
