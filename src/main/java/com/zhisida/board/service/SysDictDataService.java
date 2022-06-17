
package com.zhisida.board.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysDictData;
import com.zhisida.board.param.SysDictDataParam;

import java.util.List;

/**
 * 系统字典值service接口
 *
 * @author young-pastor
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 查询系统字典值
     *
     * @param sysDictDataParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysDictData> page(SysDictDataParam sysDictDataParam);

    /**
     * 系统字典值列表
     *
     * @param sysDictDataParam 查询参数
     * @return 系统字典值列表
     * @author young-pastor
     */
    List<SysDictData> list(SysDictDataParam sysDictDataParam);

    /**
     * 添加系统字典值
     *
     * @param sysDictDataParam 添加参数
     * @author young-pastor
     */
    void add(SysDictDataParam sysDictDataParam);

    /**
     * 删除系统字典值
     *
     * @param sysDictDataParam 删除参数
     * @author young-pastor
     */
    void delete(SysDictDataParam sysDictDataParam);

    /**
     * 编辑系统字典值
     *
     * @param sysDictDataParam 编辑参数
     * @author young-pastor
     */
    void edit(SysDictDataParam sysDictDataParam);

    /**
     * 查看系统字典值
     *
     * @param sysDictDataParam 查看参数
     * @return 系统字典值
     * @author young-pastor
     */
    SysDictData detail(SysDictDataParam sysDictDataParam);

    /**
     * 根据typeId下拉
     *
     * @param dictTypeId 字典类型id
     * @return 增强版hashMap，格式：[{"code:":"1", "value":"正常"}]
     * @author young-pastor
     */
    List<Dict> getDictDataListByDictTypeId(Long dictTypeId);

    /**
     * 根据typeId删除
     *
     * @param dictTypeId 字典类型id
     * @author young-pastor
     */
    void deleteByTypeId(Long dictTypeId);

    /**
     * 修改状态
     *
     * @param sysDictDataParam 修改参数
     * @author young-pastor
     */
    void changeStatus(SysDictDataParam sysDictDataParam);

    /**
     * 根据字典类型获取字典的code值
     *
     * @param dictTypeCodes 字典类型编码集合
     * @return 字典编码值列表
     * @author young-pastor
     */
    List<String> getDictCodesByDictTypeCode(String... dictTypeCodes);
}
