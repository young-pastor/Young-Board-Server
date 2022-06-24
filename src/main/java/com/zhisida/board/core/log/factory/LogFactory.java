
package com.zhisida.board.core.log.factory;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import com.zhisida.board.core.annotion.BusinessLog;
import com.zhisida.board.core.consts.SymbolConstant;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.enums.LogSuccessStatusEnum;
import com.zhisida.board.core.enums.VisLogTypeEnum;
import com.zhisida.board.core.util.CryptogramUtil;
import com.zhisida.board.core.util.JoinPointUtil;
import com.zhisida.board.entity.SysOpLog;
import com.zhisida.board.entity.SysVisLog;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

/**
 * 日志对象创建工厂
 *
 * @author Young-Pastor
 */
public class LogFactory {

    /**
     * 创建登录日志
     *
     * @author Young-Pastor
     */
    static void createSysLoginLog(SysVisLog sysVisLog, String account, String successCode, String failMessage) {
        sysVisLog.setName(VisLogTypeEnum.LOGIN.getMessage());
        sysVisLog.setSuccess(successCode);

        sysVisLog.setVisType(VisLogTypeEnum.LOGIN.getCode());
        sysVisLog.setVisTime(DateTime.now());
        sysVisLog.setAccount(account);

        if (LogSuccessStatusEnum.SUCCESS.getCode().equals(successCode)) {
            sysVisLog.setMessage(VisLogTypeEnum.LOGIN.getMessage() + LogSuccessStatusEnum.SUCCESS.getMessage());
        }
        if (LogSuccessStatusEnum.FAIL.getCode().equals(successCode)) {
            sysVisLog.setMessage(VisLogTypeEnum.LOGIN.getMessage() +
                    LogSuccessStatusEnum.FAIL.getMessage() + SymbolConstant.COLON + failMessage);
        }
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getVisLogEnc()) {
            SysVisLogSign(sysVisLog);
        }
    }

    /**
     * 创建登出日志
     *
     * @author Young-Pastor
     */
    static void createSysExitLog(SysVisLog sysVisLog, String account) {
        sysVisLog.setName(VisLogTypeEnum.EXIT.getMessage());
        sysVisLog.setSuccess(LogSuccessStatusEnum.SUCCESS.getCode());
        sysVisLog.setMessage(VisLogTypeEnum.EXIT.getMessage() + LogSuccessStatusEnum.SUCCESS.getMessage());
        sysVisLog.setVisType(VisLogTypeEnum.EXIT.getCode());
        sysVisLog.setVisTime(DateTime.now());
        sysVisLog.setAccount(account);
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getVisLogEnc()) {
            SysVisLogSign(sysVisLog);
        }
    }

    /**
     * 创建操作日志
     *
     * @author Young-Pastor
     */
    static void createSysOperationLog(SysOpLog sysOpLog, String account, BusinessLog businessLog, JoinPoint joinPoint, String result) {
        fillCommonSysOpLog(sysOpLog, account, businessLog, joinPoint);
        sysOpLog.setSuccess(LogSuccessStatusEnum.SUCCESS.getCode());
        sysOpLog.setResult(result);
        sysOpLog.setMessage(LogSuccessStatusEnum.SUCCESS.getMessage());
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getOpLogEnc()) {
            SysOpLogSign(sysOpLog);
        }
    }

    /**
     * 创建异常日志
     *
     * @author Young-Pastor
     */
    static void createSysExceptionLog(SysOpLog sysOpLog, String account, BusinessLog businessLog, JoinPoint joinPoint, Exception exception) {
        fillCommonSysOpLog(sysOpLog, account, businessLog, joinPoint);
        sysOpLog.setSuccess(LogSuccessStatusEnum.FAIL.getCode());
        sysOpLog.setMessage(Arrays.toString(exception.getStackTrace()));
        SysConfigCache sysConfigCache = SpringUtil.getBean(SysConfigCache.class);
        if (sysConfigCache.getCryptogramConfigs().getOpLogEnc()) {
            SysOpLogSign(sysOpLog);
        }
    }

    /**
     * 生成通用操作日志字段
     *
     * @author Young-Pastor
     */
    private static void fillCommonSysOpLog(SysOpLog sysOpLog, String account, BusinessLog businessLog, JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();

        String methodName = joinPoint.getSignature().getName();

        String param = JoinPointUtil.getArgsJsonString(joinPoint);

        sysOpLog.setName(businessLog.title());
        sysOpLog.setOpType(businessLog.opType().ordinal());
        sysOpLog.setClassName(className);
        sysOpLog.setMethodName(methodName);
        sysOpLog.setParam(param);
        sysOpLog.setOpTime(DateTime.now());
        sysOpLog.setAccount(account);
    }

    /**
     * 构建基础访问日志
     *
     * @author Young-Pastor
     */
    public static SysVisLog genBaseSysVisLog(String ip, String location, String browser, String os) {
        SysVisLog sysVisLog = new SysVisLog();
        sysVisLog.setIp(ip);
        sysVisLog.setLocation(location);
        sysVisLog.setBrowser(browser);
        sysVisLog.setOs(os);
        return sysVisLog;
    }

    /**
     * 构建基础操作日志
     *
     * @author Young-Pastor
     */
    public static SysOpLog genBaseSysOpLog(String ip, String location, String browser, String os, String url, String method) {
        SysOpLog sysOpLog = new SysOpLog();
        sysOpLog.setIp(ip);
        sysOpLog.setLocation(location);
        sysOpLog.setBrowser(browser);
        sysOpLog.setOs(os);
        sysOpLog.setUrl(url);
        sysOpLog.setReqMethod(method);
        return sysOpLog;
    }

    /**
     * 构建登录登出日志完整性保护（数据签名）
     */
    private static SysVisLog SysVisLogSign (SysVisLog sysVisLog) {
        String sysVisLogStr = sysVisLog.toString().replaceAll(" +","");
        sysVisLog.setSignValue(CryptogramUtil.doSignature(sysVisLogStr));
        return sysVisLog;
    }

    /**
     * 构建操作日志完整性保护（摘要）
     */
    private static SysOpLog SysOpLogSign (SysOpLog sysOpLog) {
        String sysVisLogStr = sysOpLog.toString();
        sysOpLog.setSignValue(CryptogramUtil.doHashValue(sysVisLogStr));
        return sysOpLog;
    }

}
