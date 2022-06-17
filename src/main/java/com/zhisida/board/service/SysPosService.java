
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysPos;
import com.zhisida.board.param.SysPosParam;

import java.util.List;

/**
 * 系统职位service接口
 *
 * @author young-pastor
 */
public interface SysPosService extends IService<SysPos> {

    /**
     * 查询系统职位
     *
     * @param sysPosParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysPos> page(SysPosParam sysPosParam);

    /**
     * 系统职位列表
     *
     * @param sysPosParam 查询参数
     * @return 职位列表
     * @author young-pastor
     */
    List<SysPos> list(SysPosParam sysPosParam);

    /**
     * 添加系统职位
     *
     * @param sysPosParam 添加参数
     * @author young-pastor
     */
    void add(SysPosParam sysPosParam);

    /**
     * 删除系统职位
     *
     * @param sysPosParamList 删除参数集合
     * @author young-pastor
     */
    void delete(List<SysPosParam> sysPosParamList);

    /**
     * 编辑系统职位
     *
     * @param sysPosParam 编辑参数
     * @author young-pastor
     */
    void edit(SysPosParam sysPosParam);

    /**
     * 查看系统职位
     *
     * @param sysPosParam 查看参数
     * @return 系统职位
     * @author young-pastor
     */
    SysPos detail(SysPosParam sysPosParam);

    /**
     * 导出系统职位
     * @author young-pastor
     */
    void export(SysPosParam sysPosParam);
}
