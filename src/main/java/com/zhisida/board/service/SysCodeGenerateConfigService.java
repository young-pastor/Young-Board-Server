
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.SysCodeGenerate;
import com.zhisida.board.entity.SysCodeGenerateConfig;
import com.zhisida.board.param.SysCodeGenerateConfigParam;
import com.zhisida.board.result.InforMationColumnsResult;

import java.util.List;

/**
 * 代码生成详细配置service接口
 *
 * @author young-pastor
 */
public interface SysCodeGenerateConfigService extends IService<SysCodeGenerateConfig> {

    /**
     * 代码生成详细配置列表
     *
     * @author young-pastor
     */
    List<SysCodeGenerateConfig> list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 添加代码生成详细配置
     *
     * @author young-pastor
     */
    void add(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 添加代码生成详细配置列表
     *
     * @author young-pastor
     */
    void addList(List<InforMationColumnsResult> inforMationColumnsResultList, SysCodeGenerate sysCodeGenerate);

    /**
     * 删除代码生成详细配置
     *
     * @author young-pastor
     */
    void delete(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);

    /**
     * 编辑代码生成详细配置
     *
     * @author young-pastor
     */
    void edit(List<SysCodeGenerateConfigParam> sysCodeGenerateConfigParamList);

    /**
     * 查看代码生成详细配置
     *
     * @author young-pastor
     */
     SysCodeGenerateConfig detail(SysCodeGenerateConfigParam sysCodeGenerateConfigParam);
}
