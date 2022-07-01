
package com.zhisida.tasks;

import cn.hutool.log.Log;
import com.zhisida.system.cache.SysConfigCache;
import com.zhisida.core.timer.TimerTaskRunner;
import com.zhisida.system.service.SysConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时器：用来根据sys_config表中的配置，刷新ConstantContextHolder中的缓存
 * <p>
 * 防止由于直接改动数据库，而调用缓存常量时，产生数据不一致问题
 *
 * @author Young-Pastor
 */
@Component
public class RefreshConstantsTaskRunner implements TimerTaskRunner {

    private static final Log log = Log.get();

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private SysConfigCache sysConfigCache;

    @Override
    public void action(String param) {
    }

}
