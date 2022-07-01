
package com.zhisida.core.log.factory;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import com.zhisida.core.annotion.BusinessLog;
import com.zhisida.core.context.requestno.RequestNoContext;
import com.zhisida.system.entity.SysOpLog;
import com.zhisida.system.entity.SysVisLog;
import com.zhisida.system.service.SysOpLogService;
import com.zhisida.system.service.SysVisLogService;
import org.aspectj.lang.JoinPoint;

import java.util.TimerTask;


/**
 * 日志操作任务创建工厂
 *
 * @author Young-Pastor
 */
public class LogTaskFactory {

    private static final Log log = Log.get();

    private static final SysVisLogService sysVisLogService = SpringUtil.getBean(SysVisLogService.class);

    private static final SysOpLogService sysOpLogService = SpringUtil.getBean(SysOpLogService.class);

    /**
     * 登录日志
     *
     * @author Young-Pastor
     */
    public static TimerTask loginLog(SysVisLog sysVisLog, final String account, String success, String failMessage) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogFactory.createSysLoginLog(sysVisLog, account, success, failMessage);
                    sysVisLogService.save(sysVisLog);
                } catch (Exception e) {
                    log.error(">>> 创建登录日志异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
                }
            }
        };
    }

    /**
     * 登出日志
     *
     * @author Young-Pastor
     */
    public static TimerTask exitLog(SysVisLog sysVisLog, String account) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogFactory.createSysExitLog(sysVisLog, account);
                    sysVisLogService.save(sysVisLog);
                } catch (Exception e) {
                    log.error(">>> 创建退出日志异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
                }
            }
        };
    }

    /**
     * 操作日志
     *
     * @author Young-Pastor
     */
    public static TimerTask operationLog(SysOpLog sysOpLog, String account, BusinessLog businessLog, JoinPoint joinPoint, String result) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogFactory.createSysOperationLog(sysOpLog, account, businessLog, joinPoint, result);
                    sysOpLogService.save(sysOpLog);
                } catch (Exception e) {
                    log.error(">>> 创建操作日志异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
                }
            }
        };
    }

    /**
     * 异常日志
     *
     * @author Young-Pastor
     */
    public static TimerTask exceptionLog(SysOpLog sysOpLog, String account, BusinessLog businessLog, JoinPoint joinPoint, Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogFactory.createSysExceptionLog(sysOpLog, account, businessLog, joinPoint, exception);
                    sysOpLogService.save(sysOpLog);
                } catch (Exception e) {
                    log.error(">>> 创建异常日志异常，请求号为：{}，具体信息为：{}", RequestNoContext.get(), e.getMessage());
                }
            }
        };
    }
}
