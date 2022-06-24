
package com.zhisida.board.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.page.PageResult;
import me.zhyd.oauth.model.AuthUser;
import com.zhisida.board.entity.SysUser;
import com.zhisida.board.param.SysUserParam;
import com.zhisida.board.result.SysUserResult;

import java.util.List;
import java.util.Set;

/**
 * 系统用户service接口
 *
 * @author Young-Pastor
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据账号获取用户
     *
     * @param account 账号
     * @return 用户
     * @author Young-Pastor
     */
    SysUser getUserByCount(String account);

    /**
     * 查询系统用户
     *
     * @param sysUserParam 查询参数
     * @return 查询分页结果
     * @author Young-Pastor
     */
    PageResult<SysUserResult> page(SysUserParam sysUserParam);

    /**
     * 根据用户账号模糊搜索系统用户列表
     *
     * @param sysUserParam 查询参数
     * @return 增强版hashMap 格式：[{"id:":123, "firstName":"张三"}]
     * @author Young-Pastor
     */
    List<Dict> list(SysUserParam sysUserParam);

    /**
     * 增加系统用户
     *
     * @param sysUserParam 添加参数
     * @author Young-Pastor
     */
    void add(SysUserParam sysUserParam);

    /**
     * 删除系统用户
     *
     * @param sysUserParamList 删除集合
     * @author Young-Pastor
     */
    void delete(List<SysUserParam> sysUserParamList);

    /**
     * 编辑系统用户
     *
     * @param sysUserParam 编辑参数
     * @author Young-Pastor
     */
    void edit(SysUserParam sysUserParam);

    /**
     * 查看系统用户
     *
     * @param sysUserParam 查看参数
     * @return 用户结果集
     * @author Young-Pastor
     */
    SysUserResult detail(SysUserParam sysUserParam);

    /**
     * 修改状态
     *
     * @param sysUserParam 修改参数
     * @author Young-Pastor
     */
    void changeStatus(SysUserParam sysUserParam);

    /**
     * 授权角色
     *
     * @param sysUserParam 授权参数
     * @author Young-Pastor
     */
    void grantRole(SysUserParam sysUserParam);

    /**
     * 授权数据
     *
     * @param sysUserParam 授权参数
     * @author Young-Pastor
     */
    void grantData(SysUserParam sysUserParam);

    /**
     * 更新信息
     *
     * @param sysUserParam 更新参数
     * @author Young-Pastor
     */
    void updateInfo(SysUserParam sysUserParam);

    /**
     * 修改密码
     *
     * @param sysUserParam 修改密码参数
     * @author Young-Pastor
     */
    void updatePwd(SysUserParam sysUserParam);

    /**
     * 获取用户的数据范围（组织机构id集合）
     *
     * @param userId 用户id
     * @param orgId  组织机构id
     * @return 数据范围id集合（组织机构id集合）
     * @author Young-Pastor
     */
    List<Long> getUserDataScopeIdList(Long userId, Long orgId);

    /**
     * 根据用户id获取姓名
     *
     * @param userId 用户id
     * @return 用户姓名
     * @author Young-Pastor
     */
    String getNameByUserId(Long userId);

    /**
     * 拥有角色
     *
     * @param sysUserParam 查询参数
     * @return 角色id集合
     * @author Young-Pastor
     */
    List<Long> ownRole(SysUserParam sysUserParam);

    /**
     * 拥有数据
     *
     * @param sysUserParam 查询参数
     * @return 数据范围id集合
     * @author Young-Pastor
     */
    List<Long> ownData(SysUserParam sysUserParam);

    /**
     * 重置密码
     *
     * @param sysUserParam 重置参数
     * @author Young-Pastor
     */
    void resetPwd(SysUserParam sysUserParam);

    /**
     * 修改头像
     *
     * @param sysUserParam 修改头像参数
     * @author Young-Pastor
     */
    void updateAvatar(SysUserParam sysUserParam);

    /**
     * 导出用户
     *
     * @param sysUserParam 导出参数
     * @author Young-Pastor
     */
    void export(SysUserParam sysUserParam);

    /**
     * 用户选择器
     *
     * @param sysUserParam 查询参数
     * @return 用户列表集合，格式[{"id":123,"name":"张三"},{"id":456,"name":"李四"}]
     * @author Young-Pastor
     */
    List<Dict> selector(SysUserParam sysUserParam);

    /**
     * 根据用户id获取用户
     *
     * @param userId 用户id
     * @return 用户实体
     * @author Young-Pastor
     **/
    SysUser getUserById(Long userId);

    /**
     * 将授权的用户信息保存到用户表
     *
     * @param authUser 授权的用户信息
     * @param sysUser  用户表信息
     * @return void
     * @author Young-Pastor
     **/
    void saveAuthUserToUser(AuthUser authUser, SysUser sysUser);

    /**
     * 获取用户id集合
     *
     * @return 用户id集合
     * @author Young-Pastor
     **/
    List<Long> getAllUserIdList();

    /**
     * 判断集合内用户是否均已删除
     *
     * @author Young-Pastor
     * @param userIdSet 用户id集合
     * @return boolean
     **/
    boolean hasAllDeletedUser(Set<Long> userIdSet);
}
