
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.SysUserRole;
import com.zhisida.board.param.SysUserParam;

import java.util.List;

/**
 * 系统用户角色service接口
 *
 * @author young-pastor
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     * @author young-pastor
     */
    List<Long> getUserRoleIdList(Long userId);

    /**
     * 授权角色
     *
     * @param sysUserParam 授权参数
     * @author young-pastor
     */
    void grantRole(SysUserParam sysUserParam);

    /**
     * 获取用户所有角色的数据范围（组织机构id集合）
     *
     * @param userId 用户id
     * @param orgId  组织机构id
     * @return 数据范围id集合（组织机构id集合）
     * @author young-pastor
     */
    List<Long> getUserRoleDataScopeIdList(Long userId, Long orgId);

    /**
     * 根据角色id删除对应的用户-角色表关联信息
     *
     * @param roleId 角色id
     * @author young-pastor
     */
    void deleteUserRoleListByRoleId(Long roleId);

    /**
     * 根据用户id删除对应的用户-角色表关联信息
     *
     * @param userId 用户id
     * @author young-pastor
     */
    void deleteUserRoleListByUserId(Long userId);
}
