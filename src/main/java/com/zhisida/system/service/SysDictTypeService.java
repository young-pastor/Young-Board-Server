
package com.zhisida.system.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.system.entity.SysDictType;
import com.zhisida.system.param.SysDictTypeParam;
import com.zhisida.system.result.SysDictTreeNode;

import java.util.List;

/**
 * 系统字典类型service接口
 *
 * @author Young-Pastor
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 查询系统字典类型
     *
     * @param sysDictTypeParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysDictType> page(SysDictTypeParam sysDictTypeParam);

    /**
     * 获取字典类型列表
     *
     * @param sysDictTypeParam 查询参数
     * @return 系统字典类型列表
     * @author Young-Pastor
     */
    List<SysDictType> list(SysDictTypeParam sysDictTypeParam);

    /**
     * 系统字典类型下拉
     *
     * @param sysDictTypeParam 下拉参数
     * @return 增强版hashMap，格式：[{"code:":"1", "value":"正常"}]
     * @author Young-Pastor
     */
    List<Dict> dropDown(SysDictTypeParam sysDictTypeParam);

    /**
     * 添加系统字典类型
     *
     * @param sysDictTypeParam 添加参数
     * @author Young-Pastor
     */
    void add(SysDictTypeParam sysDictTypeParam);

    /**
     * 删除系统字典类型
     *
     * @param sysDictTypeParam 删除参数
     * @author Young-Pastor
     */
    void delete(SysDictTypeParam sysDictTypeParam);

    /**
     * 编辑系统字典类型
     *
     * @param sysDictTypeParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysDictTypeParam sysDictTypeParam);

    /**
     * 查看系统字典类型
     *
     * @param sysDictTypeParam 查看参数
     * @return 系统字典类型
     * @author Young-Pastor
     */
    SysDictType detail(SysDictTypeParam sysDictTypeParam);

    /**
     * 修改状态（字典 0正常 1停用 2删除）
     *
     * @param sysDictTypeParam 修改参数
     * @author Young-Pastor
     */
    void changeStatus(SysDictTypeParam sysDictTypeParam);

    /**
     * 系统字典类型与字典值构造的树
     *
     * @return 树
     * @author Young-Pastor
     */
    List<SysDictTreeNode> tree();
}
