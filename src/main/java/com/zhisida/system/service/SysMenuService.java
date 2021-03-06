
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.core.pojo.node.LoginMenuTreeNode;
import com.zhisida.system.entity.SysMenu;
import com.zhisida.core.context.node.MenuBaseTreeNode;
import com.zhisida.system.param.SysMenuParam;

import java.util.List;

/**
 * 系统菜单service接口
 *
 * @author Young-Pastor
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限相关信息
     *
     * @param userId 用户id
     * @param menuIdList 菜单id集合
     * @return 权限集合
     * @author Young-Pastor
     */
    List<String> getLoginPermissions(Long userId, List<Long> menuIdList);

    /**
     * 获取用户AntDesign菜单相关信息，前端使用
     *
     * @param userId  用户id
     * @param appCode 应用编码
     * @param menuIdList 菜单id集合
     * @return AntDesign菜单信息结果集
     * @author Young-Pastor
     */
    List<SysMenu> getLoginMenus(Long userId, String appCode, List<Long> menuIdList);

    /**
     * 获取用户菜单所属的应用编码集合
     *
     * @param userId 用户id
     * @param roleIdList 角色id集合
     * @return 用户菜单所属的应用编码集合
     * @author Young-Pastor
     */
    List<String> getUserMenuAppCodeList(Long userId, List<Long> roleIdList);

    /**
     * 系统菜单列表（树表）
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树表列表
     * @author Young-Pastor
     */
    List<SysMenu> list(SysMenuParam sysMenuParam);

    /**
     * 添加系统菜单
     *
     * @param sysMenuParam 添加参数
     * @author Young-Pastor
     */
    void add(SysMenuParam sysMenuParam);

    /**
     * 删除系统菜单
     *
     * @param sysMenuParam 删除参数
     * @author Young-Pastor
     */
    void delete(SysMenuParam sysMenuParam);

    /**
     * 编辑系统菜单
     *
     * @param sysMenuParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysMenuParam sysMenuParam);

    /**
     * 查看系统菜单
     *
     * @param sysMenuParam 查看参数
     * @return 系统菜单
     * @author Young-Pastor
     */
    SysMenu detail(SysMenuParam sysMenuParam);

    /**
     * 获取系统菜单树，用于新增，编辑时选择上级节点
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树列表
     * @author Young-Pastor
     */
    List<MenuBaseTreeNode> tree(SysMenuParam sysMenuParam);

    /**
     * 获取系统菜单树，用于给角色授权时选择
     *
     * @param sysMenuParam 查询参数
     * @return 菜单树列表
     * @author Young-Pastor
     */
    List<MenuBaseTreeNode> treeForGrant(SysMenuParam sysMenuParam);

    /**
     * 根据应用编码判断该机构下是否有状态为正常的菜单
     *
     * @param appCode 应用编码
     * @return 该应用下是否有正常菜单，true是，false否
     * @author Young-Pastor
     */
    boolean hasMenu(String appCode);

    /**
     * 将SysMenu格式菜单转换为LoginMenuTreeNode菜单
     *
     * @author Young-Pastor
     * @param sysMenuList 原始菜单集合
     * @return LoginMenuTreeNode菜单集合
     */
    List<LoginMenuTreeNode> convertSysMenuToLoginMenu(List<SysMenu> sysMenuList);
}
