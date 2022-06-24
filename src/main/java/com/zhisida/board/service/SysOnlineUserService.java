
package com.zhisida.board.service;

import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.param.SysOnlineUserParam;
import com.zhisida.board.result.SysOnlineUserResult;

/**
 * 系统在线用户service接口
 *
 * @author Young-Pastor
 */
public interface SysOnlineUserService {

    /**
     * 系统在线用户列表
     *
     * @param sysOnlineUserParam 查询参数
     * @return 在线用户列表
     * @author Young-Pastor
     */
    PageResult<SysOnlineUserResult> list(SysOnlineUserParam sysOnlineUserParam);

    /**
     * 系统在线用户强退
     *
     * @param sysOnlineUserParam 操作参数
     * @author Young-Pastor
     */
    void forceExist(SysOnlineUserParam sysOnlineUserParam);
}
