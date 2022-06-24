
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysNotice;
import com.zhisida.board.param.SysNoticeParam;
import com.zhisida.board.result.SysNoticeDetailResult;
import com.zhisida.board.result.SysNoticeReceiveResult;

/**
 * 系统通知公告service接口
 *
 * @author Young-Pastor
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 查询系统通知公告
     *
     * @param sysNoticeParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysNotice> page(SysNoticeParam sysNoticeParam);

    /**
     * 添加系统通知公告
     *
     * @param sysNoticeParam 添加参数
     * @author Young-Pastor
     */
    void add(SysNoticeParam sysNoticeParam);

    /**
     * 删除系统通知公告
     *
     * @param sysNoticeParam 删除参数
     * @author Young-Pastor
     */
    void delete(SysNoticeParam sysNoticeParam);

    /**
     * 编辑系统通知公告
     *
     * @param sysNoticeParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysNoticeParam sysNoticeParam);

    /**
     * 查看系统通知公告
     *
     * @param sysNoticeParam 查看参数
     * @return 通知公告详情结果
     * @author Young-Pastor
     */
    SysNoticeDetailResult detail(SysNoticeParam sysNoticeParam);

    /**
     * 修改状态
     *
     * @param sysNoticeParam 修改参数
     * @author Young-Pastor
     */
    void changeStatus(SysNoticeParam sysNoticeParam);

    /**
     * 查询当前登陆用户已收通知公告
     *
     * @param sysNoticeParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysNoticeReceiveResult> received(SysNoticeParam sysNoticeParam);
}
