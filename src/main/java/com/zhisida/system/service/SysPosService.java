
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.system.entity.SysPos;
import com.zhisida.system.param.SysPosParam;

import java.util.List;

/**
 * 系统职位service接口
 *
 * @author Young-Pastor
 */
public interface SysPosService extends IService<SysPos> {

    /**
     * 查询系统职位
     *
     * @param sysPosParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysPos> page(SysPosParam sysPosParam);

    /**
     * 系统职位列表
     *
     * @param sysPosParam 查询参数
     * @return 职位列表
     * @author Young-Pastor
     */
    List<SysPos> list(SysPosParam sysPosParam);

    /**
     * 添加系统职位
     *
     * @param sysPosParam 添加参数
     * @author Young-Pastor
     */
    void add(SysPosParam sysPosParam);

    /**
     * 删除系统职位
     *
     * @param sysPosParamList 删除参数集合
     * @author Young-Pastor
     */
    void delete(List<SysPosParam> sysPosParamList);

    /**
     * 编辑系统职位
     *
     * @param sysPosParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysPosParam sysPosParam);

    /**
     * 查看系统职位
     *
     * @param sysPosParam 查看参数
     * @return 系统职位
     * @author Young-Pastor
     */
    SysPos detail(SysPosParam sysPosParam);

    /**
     * 导出系统职位
     * @author Young-Pastor
     */
    void export(SysPosParam sysPosParam);
}
