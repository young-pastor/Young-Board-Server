
package com.zhisida.board.service;

import com.zhisida.board.result.SysMachineResult;

/**
 * 系统属性监控service接口
 *
 * @author young-pastor
 */
public interface SysMachineService {

    /**
     * 系统属性监控
     *
     * @return 系统属性结果集
     * @author young-pastor
     */
    SysMachineResult query();
}
