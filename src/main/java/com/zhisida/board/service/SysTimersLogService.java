
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysTimersLog;
import com.zhisida.board.param.SysTimersLogParam;
import java.util.List;

/**
 * 任务日志service接口
 *
 * @author Young-Pastor
 * @date 2022-06-24 17:02:32
 */
public interface SysTimersLogService extends IService<SysTimersLog> {

    /**
     * 查询任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    PageResult<SysTimersLog> page(SysTimersLogParam sysTimersLogParam);

    /**
     * 任务日志列表
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    List<SysTimersLog> list(SysTimersLogParam sysTimersLogParam);

    /**
     * 添加任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    void add(SysTimersLogParam sysTimersLogParam);

    /**
     * 删除任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    void delete(List<SysTimersLogParam> sysTimersLogParamList);

    /**
     * 编辑任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
    void edit(SysTimersLogParam sysTimersLogParam);

    /**
     * 查看任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
     SysTimersLog detail(SysTimersLogParam sysTimersLogParam);

    /**
     * 导出任务日志
     *
     * @author Young-Pastor
     * @date 2022-06-24 17:02:32
     */
     void export(SysTimersLogParam sysTimersLogParam);

    void delete(SysTimersLogParam sysTimersLogParam);
}
