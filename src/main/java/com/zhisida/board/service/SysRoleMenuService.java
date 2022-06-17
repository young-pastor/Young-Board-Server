
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.SysRoleMenu;
import com.zhisida.board.param.SysRoleParam;

import java.util.List;

/**
 * 系统角色菜单service接口
 *
 * @author young-pastor
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色的菜单id集合
     *
     * @param roleIdList 角色id集合
     * @return 菜单id集合
     * @author young-pastor
     */
    List<Long> getRoleMenuIdList(List<Long> roleIdList);

    /**
     * 授权菜单
     *
     * @param sysRoleParam 授权参数
     * @author young-pastor
     */
    void grantMenu(SysRoleParam sysRoleParam);

    /**
     * 根据菜单id集合删除对应的角色-菜单表信息
     *
     * @param menuIdList 菜单id集合
     * @author young-pastor
     */
    void deleteRoleMenuListByMenuIdList(List<Long> menuIdList);

    /**
     * 根据角色id删除对应的角色-菜单表关联信息
     *
     * @param roleId 角色id
     * @author young-pastor
     */
    void deleteRoleMenuListByRoleId(Long roleId);
}
