
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.system.entity.SysCodeGenerate;
import com.zhisida.system.entity.SysCodeGenerateConfig;
import com.zhisida.system.param.SysCodeGenerateConfigParam;
import com.zhisida.system.result.InforMationColumnsResult;

import java.util.List;

/**
 * 代码生成详细配置service接口
 *
 * @author Young-Pastor
 */
public interface SysCodeGenerateConfigService extends IService<SysCodeGenerateConfig> {

    /**
     * 代码生成详细配置列表
     *
     * @author Young-Pastor
     */
    List<SysCodeGenerateConfig> list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 添加代码生成详细配置
     *
     * @author Young-Pastor
     */
    void add(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 添加代码生成详细配置列表
     *
     * @author Young-Pastor
     */
    void addList(List<InforMationColumnsResult> inforMationColumnsResultList, SysCodeGenerate sysCodeGenerate);

    /**
     * 删除代码生成详细配置
     *
     * @author Young-Pastor
     */
    void delete(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 编辑代码生成详细配置
     *
     * @author Young-Pastor
     */
    void edit(List<SysCodeGenerateConfigParam> sysCodeGenerateConfigParamList);

    /**
     * 查看代码生成详细配置
     *
     * @author Young-Pastor
     */
     SysCodeGenerateConfig detail(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);
}
