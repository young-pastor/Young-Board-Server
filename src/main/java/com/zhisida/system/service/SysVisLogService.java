
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.system.entity.SysVisLog;
import com.zhisida.system.param.SysVisLogParam;

/**
 * 系统访问日志service接口
 *
 * @author Young-Pastor
 */
public interface SysVisLogService extends IService<SysVisLog> {

    /**
     * 查询系统访问日志
     *
     * @param sysVisLogParam 查询参数
     * @return 查询结果集合
     * @author Young-Pastor
     */
    PageResult<SysVisLog> page(SysVisLogParam sysVisLogParam);

    /**
     * 清空系统访问日志
     *
     * @author Young-Pastor
     */
    void delete();

    /**
     * 导出系统访问日志
     *
     * @author Young-Pastor
     */
    void export(SysVisLogParam sysVisLogParam);
}
