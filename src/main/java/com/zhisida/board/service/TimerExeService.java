
package com.zhisida.board.service;

/**
 * 本接口用来，屏蔽定时任务的多样性
 * <p>
 * 目前用hutool，不排除以后用别的
 *
 * @author young-pastor
 */
public interface TimerExeService {

    /**
     * 启动一个定时器
     * <p>
     * 定时任务表达式书写规范：0/2 * * * * *
     * <p>
     * 六位数，分别是：秒 分 小时 日 月 年
     *
     * @param taskId    任务id
     * @param cron      cron表达式
     * @param className 类的全名，必须是TimerTaskRunner的子类
     * @author young-pastor
     */
    void startTimer(String taskId, String cron, String className);

    /**
     * 停止一个定时器
     *
     * @param taskId 定时任务Id
     * @author young-pastor
     */
    void stopTimer(String taskId);

}
