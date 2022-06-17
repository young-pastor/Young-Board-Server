
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.node.AntdBaseTreeNode;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.param.SysOrgParam;
import com.zhisida.board.entity.SysOrg;

import java.util.List;

/**
 * 系统组织机构service接口
 *
 * @author young-pastor
 */
public interface SysOrgService extends IService<SysOrg> {

    /**
     * 查询系统机构
     *
     * @param sysOrgParam 查询参数
     * @return 查询分页结果
     * @author young-pastor
     */
    PageResult<SysOrg> page(SysOrgParam sysOrgParam);

    /**
     * 系统组织机构列表
     *
     * @param sysOrgParam 查询参数
     * @return 组织机构列表
     * @author young-pastor
     */
    List<SysOrg> list(SysOrgParam sysOrgParam);

    /**
     * 添加系统组织机构
     *
     * @param sysOrgParam 添加参数
     * @author young-pastor
     */
    void add(SysOrgParam sysOrgParam);

    /**
     * 删除系统组织机构
     *
     * @param sysOrgParamList 删除参数集合
     * @author young-pastor
     */
    void delete(List<SysOrgParam> sysOrgParamList);

    /**
     * 编辑系统组织机构
     *
     * @param sysOrgParam 编辑参数
     * @author young-pastor
     */
    void edit(SysOrgParam sysOrgParam);

    /**
     * 查看系统组织机构
     *
     * @param sysOrgParam 查看参数
     * @return 组织机构
     * @author young-pastor
     */
    SysOrg detail(SysOrgParam sysOrgParam);

    /**
     * 获取系统组织机构树
     *
     * @param sysOrgParam 查询参数
     * @return 系统组织机构树
     * @author young-pastor
     */
    List<AntdBaseTreeNode> tree(SysOrgParam sysOrgParam);

    /**
     * 根据数据范围类型获取当前登录用户的数据范围id集合
     *
     * @param dataScopeType 数据范围类型（1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据）
     * @param orgId         组织机构id
     * @return 数据范围id集合
     * @author young-pastor
     */
    List<Long> getDataScopeListByDataScopeType(Integer dataScopeType, Long orgId);

    /**
     * 导出机构数据
     *
     * @author young-pastor
     */
    void export(SysOrgParam sysOrgParam);
}
