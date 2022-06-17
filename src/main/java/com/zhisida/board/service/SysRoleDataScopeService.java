
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.SysRoleDataScope;
import com.zhisida.board.param.SysRoleParam;

import java.util.List;

/**
 * 系统角色数据范围service接口
 *
 * @author young-pastor
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScope> {

    /**
     * 授权数据
     *
     * @param sysRoleParam 授权参数
     * @author young-pastor
     */
    void grantDataScope(SysRoleParam sysRoleParam);

    /**
     * 根据角色id获取角色数据范围集合
     *
     * @param roleIdList 角色id集合
     * @return 数据范围id集合
     * @author young-pastor
     */
    List<Long> getRoleDataScopeIdList(List<Long> roleIdList);

    /**
     * 根据机构id集合删除对应的角色-数据范围关联信息
     *
     * @param orgIdList 机构id集合
     * @author young-pastor
     */
    void deleteRoleDataScopeListByOrgIdList(List<Long> orgIdList);

    /**
     * 根据角色id删除对应的角色-数据范围关联信息
     *
     * @param roleId 角色id
     * @author young-pastor
     */
    void deleteRoleDataScopeListByRoleId(Long roleId);
}
