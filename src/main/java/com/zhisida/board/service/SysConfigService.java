
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysConfig;
import com.zhisida.board.param.SysConfigParam;

import java.util.List;

/**
 * 系统参数配置service接口
 *
 * @author young-pastor
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 查询系统参数配置
     *
     * @param sysConfigParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysConfig> page(SysConfigParam sysConfigParam);

    /**
     * 查询系统参数配置
     *
     * @param sysConfigParam 查询参数
     * @return 系统参数配置列表
     * @author young-pastor
     */
    List<SysConfig> list(SysConfigParam sysConfigParam);

    /**
     * 查看系统参数配置
     *
     * @param sysConfigParam 查看参数
     * @return 系统参数配置
     * @author young-pastor
     */
    SysConfig detail(SysConfigParam sysConfigParam);

    /**
     * 添加系统参数配置
     *
     * @param sysConfigParam 添加参数
     * @author young-pastor
     */
    void add(SysConfigParam sysConfigParam);

    /**
     * 删除系统参数配置
     *
     * @param sysConfigParam 删除参数
     * @author young-pastor
     */
    void delete(SysConfigParam sysConfigParam);

    /**
     * 编辑系统参数配置
     *
     * @param sysConfigParam 编辑参数
     * @author young-pastor
     */
    void edit(SysConfigParam sysConfigParam);

}
