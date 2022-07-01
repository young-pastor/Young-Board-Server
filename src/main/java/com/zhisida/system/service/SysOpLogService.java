
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.system.entity.SysOpLog;
import com.zhisida.system.param.SysOpLogParam;

/**
 * 系统操作日志service接口
 *
 * @author Young-Pastor
 */
public interface SysOpLogService extends IService<SysOpLog> {

    /**
     * 查询系统操作日志
     *
     * @param sysOpLogParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysOpLog> page(SysOpLogParam sysOpLogParam);

    /**
     * 清空系统操作日志
     *
     * @author Young-Pastor
     */
    void delete();

    /**
     * 导出系统操作日志
     *
     * @author Young-Pastor
     */
    void export(SysOpLogParam sysOpLogParam);
}
