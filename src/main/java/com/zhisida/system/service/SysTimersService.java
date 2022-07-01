
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.system.entity.SysTimers;
import com.zhisida.system.param.SysTimersParam;

import java.util.List;

/**
 * 定时任务 服务类
 *
 * @author Young-Pastor
 */
public interface SysTimersService extends IService<SysTimers> {

    /**
     * 分页查询定时任务
     *
     * @param sysTimersParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysTimers> page(SysTimersParam sysTimersParam);

    /**
     * 查询所有定时任务
     *
     * @param sysTimersParam 查询参数
     * @return 定时任务列表
     * @author Young-Pastor
     */
    List<SysTimers> list(SysTimersParam sysTimersParam);

    /**
     * 添加定时任务
     *
     * @param sysTimersParam 添加参数
     * @author Young-Pastor
     */
    void add(SysTimersParam sysTimersParam);

    /**
     * 删除定时任务
     *
     * @param sysTimersParam 删除参数
     * @author Young-Pastor
     */
    void delete(SysTimersParam sysTimersParam);

    /**
     * 编辑定时任务
     *
     * @param sysTimersParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysTimersParam sysTimersParam);

    /**
     * 查看详情定时任务
     *
     * @param sysTimersParam 查看参数
     * @return 定时任务
     * @author Young-Pastor
     */
    SysTimers detail(SysTimersParam sysTimersParam);

    /**
     * 启动任务
     *
     * @param sysTimersParam 启动参数
     * @author Young-Pastor
     */
    void start(SysTimersParam sysTimersParam);

    /**
     * 停止任务
     *
     * @param sysTimersParam 停止参数
     * @author Young-Pastor
     */
    void stop(SysTimersParam sysTimersParam);

    /**
     * 获取所有可执行的任务列表
     *
     * @return TimerTaskRunner的所有子类名称集合
     * @author Young-Pastor
     */
    List<String> getActionClasses();

    void execute(SysTimersParam sysTimersParam);
}
