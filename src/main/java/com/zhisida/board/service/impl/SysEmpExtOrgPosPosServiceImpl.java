
package com.zhisida.board.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.SysEmpExtOrgPos;
import com.zhisida.board.mapper.SysEmpExtOrgPosMapper;
import org.springframework.stereotype.Service;
import com.zhisida.board.service.SysEmpExtOrgPosService;
import com.zhisida.board.entity.SysOrg;
import com.zhisida.board.service.SysOrgService;
import com.zhisida.board.entity.SysPos;
import com.zhisida.board.service.SysPosService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工附属机构service接口实现类
 *
 * @author young-pastor
 */
@Service
public class SysEmpExtOrgPosPosServiceImpl extends ServiceImpl<SysEmpExtOrgPosMapper, SysEmpExtOrgPos> implements SysEmpExtOrgPosService {

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private SysPosService sysPosService;

    /**
     * 附属机构id参数键
     */
    private static final String EXT_ORG_ID_DICT_KEY = "orgId";

    /**
     * 附属机构编码参数键
     */
    private static final String EXT_ORG_CODE_DICT_KEY = "orgCode";

    /**
     * 附属机构名称参数键
     */
    private static final String EXT_ORG_NAME_DICT_KEY = "orgName";

    /**
     * 附属职位id参数键
     */
    private static final String EXT_POS_ID_DICT_KEY = "posId";

    /**
     * 附属职位编码参数键
     */
    private static final String EXT_POS_CODE_DICT_KEY = "posCode";

    /**
     * 附属职位名称参数键
     */
    private static final String EXT_POS_NAME_DICT_KEY = "posName";

    @Override
    public void addOrEdit(Long empId, List<Dict> extIdList) {
        LambdaQueryWrapper<SysEmpExtOrgPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpExtOrgPos::getEmpId, empId);
        //删除附属信息
        this.remove(queryWrapper);
        //保存附属信息
        extIdList.forEach(dict -> {
            Long orgId = dict.getLong(EXT_ORG_ID_DICT_KEY);
            Long posId = dict.getLong(EXT_POS_ID_DICT_KEY);
            SysEmpExtOrgPos sysEmpExtOrgPos = new SysEmpExtOrgPos();
            sysEmpExtOrgPos.setEmpId(empId);
            sysEmpExtOrgPos.setOrgId(orgId);
            sysEmpExtOrgPos.setPosId(posId);
            this.save(sysEmpExtOrgPos);
        });
    }

    @Override
    public List<Dict> getEmpExtOrgPosDictList(Long empId, boolean isFillId) {
        List<Dict> dictList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysEmpExtOrgPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpExtOrgPos::getEmpId, empId);

        this.list(queryWrapper).forEach(sysEmpExtOrgPos -> {
            Dict dict = Dict.create();
            Long orgId = sysEmpExtOrgPos.getOrgId();
            SysOrg sysOrg = sysOrgService.getById(orgId);
            dict.put(EXT_ORG_CODE_DICT_KEY, sysOrg.getCode());
            dict.put(EXT_ORG_NAME_DICT_KEY, sysOrg.getName());

            Long posId = sysEmpExtOrgPos.getPosId();
            SysPos sysPos = sysPosService.getById(posId);
            dict.put(EXT_POS_CODE_DICT_KEY, sysPos.getCode());
            dict.put(EXT_POS_NAME_DICT_KEY, sysPos.getName());

            if (isFillId) {
                dict.put(EXT_ORG_ID_DICT_KEY, orgId);
                dict.put(EXT_POS_ID_DICT_KEY, posId);
            }
            dictList.add(dict);
        });

        return dictList;
    }

    @Override
    public boolean hasExtOrgEmp(Long orgId) {
        LambdaQueryWrapper<SysEmpExtOrgPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpExtOrgPos::getOrgId, orgId);
        List<SysEmpExtOrgPos> list = this.list(queryWrapper);
        return list.size() != 0;
    }

    @Override
    public boolean hasExtPosEmp(Long posId) {
        LambdaQueryWrapper<SysEmpExtOrgPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpExtOrgPos::getPosId, posId);
        List<SysEmpExtOrgPos> list = this.list(queryWrapper);
        return list.size() != 0;
    }

    @Override
    public void deleteEmpExtInfoByUserId(Long empId) {
        LambdaQueryWrapper<SysEmpExtOrgPos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysEmpExtOrgPos::getEmpId, empId);
        this.remove(queryWrapper);
    }
}
