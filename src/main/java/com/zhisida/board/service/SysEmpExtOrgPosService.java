
package com.zhisida.board.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.board.entity.SysEmpExtOrgPos;

import java.util.List;

/**
 * 员工附属机构service接口
 *
 * @author young-pastor
 */
public interface SysEmpExtOrgPosService extends IService<SysEmpExtOrgPos> {

    /**
     * 保存或编辑附属机构相关信息
     *
     * @param empId     员工id（用户id）
     * @param extIdList 附属机构职位信息集合，格式：[{"orgId":1234, "posId":5678}]
     * @author young-pastor
     */
    void addOrEdit(Long empId, List<Dict> extIdList);

    /**
     * 获取附属机构和职位信息
     *
     * @param empId    员工id（用户id）
     * @param isFillId 是否需要返回id信息
     * @return 增强版hashMap，格式：[{"orgId":123, "orgCode":"yfb", "orgName":"研发部", "posId":456, "posCode":"zjl", "posName":"总经理"}]
     * @author young-pastor
     */
    List<Dict> getEmpExtOrgPosDictList(Long empId, boolean isFillId);

    /**
     * 根据机构id判断该附属机构下是否有员工
     *
     * @param orgId 机构id
     * @return 该附属机构下是否有员工，true是，false否
     * @author young-pastor
     */
    boolean hasExtOrgEmp(Long orgId);

    /**
     * 根据职位id判断该附属职位下是否有员工
     *
     * @param posId 职位id
     * @return 该附属职位下是否有员工，true是，false否
     * @author young-pastor
     */
    boolean hasExtPosEmp(Long posId);

    /**
     * 根据员工id删除对应的员工-附属信息
     *
     * @param empId 员工id（用户id）
     * @author young-pastor
     */
    void deleteEmpExtInfoByUserId(Long empId);
}
