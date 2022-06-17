
package com.zhisida.board.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.param.SysAppParam;
import com.zhisida.board.entity.SysApp;

import java.util.List;

/**
 * 系统应用service接口
 *
 * @author young-pastor
 */
public interface SysAppService extends IService<SysApp> {

    /**
     * 获取用户应用相关信息
     *
     * @param userId 用户id
     * @param roleIdList 角色id集合
     * @return 用户拥有的应用信息，格式：[{"code":"system","name":"系统应用","active":true}]
     * @author young-pastor
     */
    List<Dict> getLoginApps(Long userId, List<Long> roleIdList);

    /**
     * 查询系统应用
     *
     * @param sysAppParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysApp> page(SysAppParam sysAppParam);

    /**
     * 添加系统应用
     *
     * @param sysAppParam 添加参数
     * @author young-pastor
     */
    void add(SysAppParam sysAppParam);

    /**
     * 删除系统应用
     *
     * @param sysAppParam 删除参数
     * @author young-pastor
     */
    void delete(SysAppParam sysAppParam);

    /**
     * 编辑系统应用
     *
     * @param sysAppParam 编辑参数
     * @author young-pastor
     */
    void edit(SysAppParam sysAppParam);

    /**
     * 查看系统应用
     *
     * @param sysAppParam 查看参数
     * @return 系统应用
     * @author young-pastor
     */
    SysApp detail(SysAppParam sysAppParam);

    /**
     * 系统应用列表
     *
     * @param sysAppParam 查询参数
     * @return 系统应用列表
     * @author young-pastor
     */
    List<SysApp> list(SysAppParam sysAppParam);

    /**
     * 设为默认应用
     *
     * @param sysAppParam 设为默认应用参数
     * @author young-pastor
     */
    void setAsDefault(SysAppParam sysAppParam);
}
