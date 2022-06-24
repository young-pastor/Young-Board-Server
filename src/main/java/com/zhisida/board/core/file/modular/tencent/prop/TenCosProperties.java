
package com.zhisida.board.core.file.modular.tencent.prop;

import lombok.Data;

/**
 * 腾讯云cos文件存储配置
 *
 * @author Young-Pastor
 */
@Data
public class TenCosProperties {

    /**
     * secretId
     */
    private String secretId;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 地域id（默认北京）
     */
    private String regionId = "ap-beijing";

}
