
package com.zhisida.system.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhisida.system.entity.SysEmpPos;

import java.util.List;

/**
 * 员工职位service接口
 *
 * @author Young-Pastor
 */
public interface SysEmpPosService extends IService<SysEmpPos> {

    /**
     * 保存职位相关信息
     *
     * @param empId     员工id（用户id）
     * @param posIdList 职位id集合
     * @author Young-Pastor
     */
    void addOrEdit(Long empId, List<Long> posIdList);

    /**
     * 获取所属职位信息
     *
     * @param empId    员工id（用户id）
     * @param isFillId 是否需要返回id信息
     * @return 增强版hashMap，格式：[{"posId":456, "posCode":"zjl", "posName":"总经理"}]
     * @author Young-Pastor
     */
    List<Dict> getEmpPosDictList(Long empId, boolean isFillId);

    /**
     * 根据职位id判断该职位下是否有员工
     *
     * @param posId 职位id
     * @return 该职位下是否有员工，true是，false否
     * @author Young-Pastor
     */
    boolean hasPosEmp(Long posId);

    /**
     * 根据员工id删除对用的员工-职位信息
     *
     * @param empId 员工id（用户id）
     * @author Young-Pastor
     */
    void deleteEmpPosInfoByUserId(Long empId);
}
