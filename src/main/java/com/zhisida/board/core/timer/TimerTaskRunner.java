
package com.zhisida.board.core.timer;

/**
 * 定时器执行者
 * <p>
 * 定时器都要实现本接口，并需要把实现类加入到spring容器中
 *
 * @author young-pastor
 */
public interface TimerTaskRunner {

    /**
     * 任务执行的具体内容
     *
     * @author young-pastor
     */
    void action(String param);

}
