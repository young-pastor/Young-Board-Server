
package com.zhisida.config;

import cn.hutool.core.util.ObjectUtil;
import com.zhisida.core.file.modular.local.LocalFileOperator;
import com.zhisida.core.file.modular.local.prop.LocalFileProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.file.FileOperator;

/**
 * 文件存储的配置
 * <p>
 * 默认激活本地文件存储
 *
 * @author Young-Pastor
 */
@Configuration
public class FileConfig {

    /**
     * 默认文件存储的位置
     */
    public static final String DEFAULT_BUCKET = "defaultBucket";

    /**
     * 本地文件操作客户端
     *
     * @author Young-Pastor
     */
    @Bean
    public FileOperator fileOperator(SysConfigCache sysConfigCache) {
        LocalFileProperties localFileProperties = new LocalFileProperties();
        String fileUploadPathForWindows = sysConfigCache.getDefaultFileUploadPathForWindows();
        if (ObjectUtil.isNotEmpty(fileUploadPathForWindows)) {
            localFileProperties.setLocalFileSavePathWin(fileUploadPathForWindows);
        }

        String fileUploadPathForLinux = sysConfigCache.getDefaultFileUploadPathForLinux();
        if (ObjectUtil.isNotEmpty(fileUploadPathForLinux)) {
            localFileProperties.setLocalFileSavePathLinux(fileUploadPathForLinux);
        }
        return new LocalFileOperator(localFileProperties);
    }

}
