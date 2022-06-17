
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysOpLog;
import com.zhisida.board.param.SysOpLogParam;

/**
 * 系统操作日志service接口
 *
 * @author young-pastor
 */
public interface SysOpLogService extends IService<SysOpLog> {

    /**
     * 查询系统操作日志
     *
     * @param sysOpLogParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysOpLog> page(SysOpLogParam sysOpLogParam);

    /**
     * 清空系统操作日志
     *
     * @author young-pastor
     */
    void delete();

    /**
     * 导出系统操作日志
     *
     * @author young-pastor
     */
    void export(SysOpLogParam sysOpLogParam);
}