
package com.zhisida.board.enums;

import lombok.Getter;

/**
 * 文件存储位置
 *
 * @author Young-Pastor
 */
@Getter
public enum SysFileLocationEnum {

    /**
     * 阿里云
     */
    ALIYUN(1),

    /**
     * 腾讯云
     */
    TENCENT(2),

    /**
     * minio服务器
     */
    MINIO(3),

    /**
     * 本地
     */
    LOCAL(4);

    private final Integer code;

    SysFileLocationEnum(int code) {
        this.code = code;
    }

}
