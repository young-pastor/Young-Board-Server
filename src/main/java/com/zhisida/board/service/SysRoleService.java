
package com.zhisida.board.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysRole;
import com.zhisida.board.param.SysRoleParam;

import java.util.List;

/**
 * 系统角色service接口
 *
 * @author Young-Pastor
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取用户角色相关信息
     *
     * @param userId 用户id
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author Young-Pastor
     */
    List<Dict> getLoginRoles(Long userId);

    /**
     * 查询系统角色
     *
     * @param sysRoleParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysRole> page(SysRoleParam sysRoleParam);

    /**
     * 根据角色名模糊搜索系统角色列表
     *
     * @param sysRoleParam 查询参数
     * @return 增强版hashMap，格式：[{"id":456, "name":"总经理(zjl)"}]
     * @author Young-Pastor
     */
    List<Dict> list(SysRoleParam sysRoleParam);

    /**
     * 系统角色下拉（用于授权角色时选择）
     *
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author Young-Pastor
     */
    List<Dict> dropDown();

    /**
     * 添加系统角色
     *
     * @param sysRoleParam 添加参数
     * @author Young-Pastor
     */
    void add(SysRoleParam sysRoleParam);

    /**
     * 删除系统角色
     *
     * @param sysRoleParam 删除参数
     * @author Young-Pastor
     */
    void delete(SysRoleParam sysRoleParam);

    /**
     * 编辑系统角色
     *
     * @param sysRoleParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysRoleParam sysRoleParam);

    /**
     * 查看系统角色
     *
     * @param sysRoleParam 查看参数
     * @return 系统角色
     * @author Young-Pastor
     */
    SysRole detail(SysRoleParam sysRoleParam);

    /**
     * 授权菜单
     *
     * @param sysRoleParam 授权参数
     * @author Young-Pastor
     */
    void grantMenu(SysRoleParam sysRoleParam);

    /**
     * 授权数据
     *
     * @param sysRoleParam 授权参数
     * @author Young-Pastor
     */
    void grantData(SysRoleParam sysRoleParam);

    /**
     * 根据角色id集合获取数据范围id集合
     *
     * @param roleIdList 角色id集合
     * @param orgId      机构id
     * @return 数据范围id集合
     * @author Young-Pastor
     */
    List<Long> getUserDataScopeIdList(List<Long> roleIdList, Long orgId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     * @return 角色名称
     * @author Young-Pastor
     */
    String getNameByRoleId(Long roleId);

    /**
     * 查询角色拥有菜单
     *
     * @param sysRoleParam 查询参数
     * @return 菜单id集合
     * @author Young-Pastor
     */
    List<Long> ownMenu(SysRoleParam sysRoleParam);

    /**
     * 查询角色拥有数据
     *
     * @param sysRoleParam 查询参数
     * @return 数据范围id集合
     * @author Young-Pastor
     */
    List<Long> ownData(SysRoleParam sysRoleParam);
}
