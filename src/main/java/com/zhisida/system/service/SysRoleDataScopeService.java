
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.system.entity.SysRoleDataScope;
import com.zhisida.system.param.SysRoleParam;

import java.util.List;

/**
 * 系统角色数据范围service接口
 *
 * @author Young-Pastor
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScope> {

    /**
     * 授权数据
     *
     * @param sysRoleParam 授权参数
     * @author Young-Pastor
     */
    void grantDataScope(SysRoleParam sysRoleParam);

    /**
     * 根据角色id获取角色数据范围集合
     *
     * @param roleIdList 角色id集合
     * @return 数据范围id集合
     * @author Young-Pastor
     */
    List<Long> getRoleDataScopeIdList(List<Long> roleIdList);

    /**
     * 根据机构id集合删除对应的角色-数据范围关联信息
     *
     * @param orgIdList 机构id集合
     * @author Young-Pastor
     */
    void deleteRoleDataScopeListByOrgIdList(List<Long> orgIdList);

    /**
     * 根据角色id删除对应的角色-数据范围关联信息
     *
     * @param roleId 角色id
     * @author Young-Pastor
     */
    void deleteRoleDataScopeListByRoleId(Long roleId);
}
