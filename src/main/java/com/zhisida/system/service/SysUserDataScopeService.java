
package com.zhisida.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.system.entity.SysUserDataScope;
import com.zhisida.system.param.SysUserParam;

import java.util.List;

/**
 * 系统用户数据范围service接口
 *
 * @author Young-Pastor
 */
public interface SysUserDataScopeService extends IService<SysUserDataScope> {

    /**
     * 授权数据
     *
     * @param sysUserParam 授权参数
     * @author Young-Pastor
     */
    void grantData(SysUserParam sysUserParam);

    /**
     * 获取用户的数据范围id集合
     *
     * @param uerId 用户id
     * @return 数据范围id集合
     * @author Young-Pastor
     */
    List<Long> getUserDataScopeIdList(Long uerId);

    /**
     * 根据机构id集合删除对应的用户-数据范围关联信息
     *
     * @param orgIdList 机构id集合
     * @author Young-Pastor
     */
    void deleteUserDataScopeListByOrgIdList(List<Long> orgIdList);

    /**
     * 根据用户id删除对应的用户-数据范围关联信息
     *
     * @param userId 用户id
     * @author Young-Pastor
     */
    void deleteUserDataScopeListByUserId(Long userId);
}
