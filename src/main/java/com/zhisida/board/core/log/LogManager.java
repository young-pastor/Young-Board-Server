
package com.zhisida.board.core.log;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.exception.enums.ServerExceptionEnum;
import com.zhisida.board.core.util.HttpServletUtil;
import com.zhisida.board.core.util.IpAddressUtil;
import com.zhisida.board.core.util.UaUtil;
import com.zhisida.board.core.log.factory.LogFactory;
import com.zhisida.board.core.log.factory.LogTaskFactory;
import com.zhisida.board.entity.SysOpLog;
import com.zhisida.board.entity.SysVisLog;

import javax.servlet.http.HttpServletRequest;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 日志管理器
 *
 * @author Young-Pastor
 */
public class LogManager {

    /**
     * 异步操作记录日志的线程池
     */
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(10, new ScheduledExecutorFactoryBean());

    private LogManager() {
    }

    private static final LogManager LOG_MANAGER = new LogManager();

    public static LogManager me() {
        return LOG_MANAGER;
    }

    /**
     * 异步执行日志的方法
     *
     * @author Young-Pastor
     */
    private void executeLog(TimerTask task) {

        //如果演示模式开启，则不记录日志
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getDemoEnvFlag()) {
            return;
        }

        //日志记录操作延时
        int operateDelayTime = 10;
        EXECUTOR.schedule(task, operateDelayTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 登录日志
     *
     * @author Young-Pastor
     */
    public void executeLoginLog(final String account, final String success, final String failMessage) {
        SysVisLog sysVisLog = this.genBaseSysVisLog();
        TimerTask timerTask = LogTaskFactory.loginLog(sysVisLog, account,
                success,
                failMessage);
        executeLog(timerTask);
    }

    /**
     * 登出日志
     *
     * @author Young-Pastor
     */
    public void executeExitLog(final String account) {
        SysVisLog sysVisLog = this.genBaseSysVisLog();
        TimerTask timerTask = LogTaskFactory.exitLog(sysVisLog, account);
        executeLog(timerTask);
    }

    /**
     * 操作日志
     *
     * @author Young-Pastor
     */
    public void executeOperationLog(BusinessLog businessLog, final String account, JoinPoint joinPoint, final String result) {
        SysOpLog sysOpLog = this.genBaseSysOpLog();
        TimerTask timerTask = LogTaskFactory.operationLog(sysOpLog, account, businessLog, joinPoint, result);
        executeLog(timerTask);
    }

    /**
     * 异常日志
     *
     * @author Young-Pastor
     */
    public void executeExceptionLog(BusinessLog businessLog, final String account, JoinPoint joinPoint, Exception exception) {
        SysOpLog sysOpLog = this.genBaseSysOpLog();
        TimerTask timerTask = LogTaskFactory.exceptionLog(sysOpLog, account, businessLog, joinPoint, exception);
        executeLog(timerTask);
    }

    /**
     * 构建基础访问日志
     *
     * @author Young-Pastor
     */
    private SysVisLog genBaseSysVisLog() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            String ip = IpAddressUtil.getIp(request);
            String address = IpAddressUtil.getAddress(request);
            String browser = UaUtil.getBrowser(request);
            String os = UaUtil.getOs(request);
            return LogFactory.genBaseSysVisLog(ip, address, browser, os);
        } else {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        }
    }

    /**
     * 构建基础操作日志
     *
     * @author Young-Pastor
     */
    private SysOpLog genBaseSysOpLog() {
        HttpServletRequest request = HttpServletUtil.getRequest();
        if (ObjectUtil.isNotNull(request)) {
            String ip = IpAddressUtil.getIp(request);
            String address = IpAddressUtil.getAddress(request);
            String browser = UaUtil.getBrowser(request);
            String os = UaUtil.getOs(request);
            String url = request.getRequestURI();
            String method = request.getMethod();
            return LogFactory.genBaseSysOpLog(ip, address, browser, os, url, method);
        } else {
            throw new ServiceException(ServerExceptionEnum.REQUEST_EMPTY);
        }
    }

}
