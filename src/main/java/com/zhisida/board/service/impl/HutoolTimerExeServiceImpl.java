
package com.zhisida.board.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.timer.TimerTaskRunner;
import com.zhisida.board.entity.SysTimers;
import com.zhisida.board.entity.SysTimersLog;
import com.zhisida.board.enums.exp.SysTimersExceptionEnum;
import com.zhisida.board.service.SysTimersLogService;
import com.zhisida.board.service.TimerExeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * hutool方式的定时任务执行
 *
 * @author Young-Pastor
 */
@Service
public class HutoolTimerExeServiceImpl implements TimerExeService {
    @Resource
    SysTimersLogService sysTimersLogService;

    private static final Log log = Log.get();

    @Override
    public void startTimer(SysTimers sysTimers) {
        Long timersId = sysTimers.getId();
        String taskId = String.valueOf(timersId);
        String cron = sysTimers.getCron();
        String param = sysTimers.getParam();
        String className = sysTimers.getActionClass();
        if (ObjectUtil.hasEmpty(taskId, cron, className)) {
            throw new ServiceException(SysTimersExceptionEnum.EXE_EMPTY_PARAM);
        }
        // 预加载类看是否存在此定时任务类
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ServiceException(SysTimersExceptionEnum.TIMER_NOT_EXISTED);
        }

        // 定义hutool的任务
        Task task = () -> {
            SysTimersLog sysTimersLog = new SysTimersLog();
            try {
                sysTimersLog.setTimerId(timersId);
                sysTimersLog.setExecuteParam(param);
                sysTimersLog.setExecuteStartTime(new Date());
                TimerTaskRunner timerTaskRunner = (TimerTaskRunner) SpringUtil.getBean(Class.forName(className));
                timerTaskRunner.action(param);
                sysTimersLog.setExecuteEndTime(new Date());
                sysTimersLog.setExecuteCode("SUCCESS");
                sysTimersLog.setExecuteMsg("执行成功");
            } catch (ClassNotFoundException e) {
                log.error(">>> 任务执行异常：{}", e.getMessage());
                sysTimersLog.setExecuteEndTime(new Date());
                sysTimersLog.setExecuteCode("FAIL");
                sysTimersLog.setExecuteMsg(e.getMessage());
            } finally {
                sysTimersLogService.save(sysTimersLog);
            }
        };

        // 开始执行任务
        CronUtil.schedule(taskId, cron, task);
    }

    @Override
    public void stopTimer(String taskId) {
        CronUtil.remove(taskId);
    }

    @Override
    public void execute(SysTimers sysTimers) {

        Long taskId = sysTimers.getId();
        String param = sysTimers.getParam();
        String className = sysTimers.getActionClass();
        SysTimersLog sysTimersLog = new SysTimersLog();
        sysTimersLog.setTimerId(taskId);
        sysTimersLog.setExecuteParam(param);
        sysTimersLog.setExecuteStartTime(new Date());
        try {
            TimerTaskRunner timerTaskRunner = (TimerTaskRunner) SpringUtil.getBean(Class.forName(className));
            timerTaskRunner.action(param);
            sysTimersLog.setExecuteEndTime(new Date());
            sysTimersLog.setExecuteCode("SUCCESS");
            sysTimersLog.setExecuteMsg("执行成功");
        } catch (Exception e) {
            sysTimersLog.setExecuteEndTime(new Date());
            sysTimersLog.setExecuteCode("FAIL");
            sysTimersLog.setExecuteMsg(e.getMessage());
            log.error(">>> 任务执行异常：{}", e.getMessage());
        } finally {
            sysTimersLogService.save(sysTimersLog);
        }
    }

}
