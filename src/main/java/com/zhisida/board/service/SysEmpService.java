
package com.zhisida.board.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.core.pojo.login.LoginEmpInfo;
import com.zhisida.board.result.SysEmpInfo;
import com.zhisida.board.entity.SysEmp;
import com.zhisida.board.param.SysEmpParam;

/**
 * 员工service接口
 *
 * @author young-pastor
 */
public interface SysEmpService extends IService<SysEmp> {

    /**
     * 获取登录用户员工相关信息
     *
     * @param empId 员工id（用户id）
     * @return 登录用户员工相关信息
     * @author young-pastor
     */
    LoginEmpInfo getLoginEmpInfo(Long empId);

    /**
     * 获取用户员工相关信息
     *
     * @param empId 员工id（用户id）
     * @return 用户员工相关信息
     * @author young-pastor
     */
    SysEmpInfo getSysEmpInfo(Long empId);

    /**
     * 增加或编辑员工相关信息
     *
     * @param sysEmpParam 增加或编辑参数
     * @author young-pastor
     */
    void addOrUpdate(SysEmpParam sysEmpParam);

    /**
     * 修改员工相关机构信息
     *
     * @param orgId   机构id
     * @param orgName 机构名称
     * @author young-pastor
     */
    void updateEmpOrgInfo(Long orgId, String orgName);

    /**
     * 根据机构id判断该机构下是否有员工
     *
     * @param orgId 机构id
     * @return 该机构下是否有员工，true是，false否
     * @author young-pastor
     */
    boolean hasOrgEmp(Long orgId);

    /**
     * 根据员工id删除对应的员工表信息
     *
     * @param empId 员工id（用户id）
     * @author young-pastor
     */
    void deleteEmpInfoByUserId(Long empId);
}
