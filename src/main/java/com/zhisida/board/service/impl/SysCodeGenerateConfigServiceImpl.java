
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.entity.SysCodeGenerate;
import com.zhisida.board.entity.SysCodeGenerateConfig;
import com.zhisida.board.enums.SysCodeGenerateConfigExceptionEnum;
import com.zhisida.board.result.InforMationColumnsResult;
import com.zhisida.board.service.SysCodeGenerateConfigService;
import com.zhisida.board.mapper.SysCodeGenerateConfigMapper;
import com.zhisida.board.param.SysCodeGenerateConfigParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhisida.board.core.enums.YesOrNotEnum;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.consts.GenConstant;
import com.zhisida.board.core.enums.QueryTypeEnum;
import com.zhisida.board.core.enums.TableFilteredFieldsEnum;
import com.zhisida.board.core.util.JavaEffUtil;
import com.zhisida.board.core.util.JavaSqlUtil;
import com.zhisida.board.core.util.NamingConUtil;

import java.util.List;

/**
 * 代码生成详细配置service接口实现类
 *
 * @author Young-Pastor
 */
@Service
public class SysCodeGenerateConfigServiceImpl extends ServiceImpl<SysCodeGenerateConfigMapper, SysCodeGenerateConfig> implements SysCodeGenerateConfigService {

    @Override
    public List<SysCodeGenerateConfig> list(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        LambdaQueryWrapper<SysCodeGenerateConfig> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysCodeGenerateConfigParam)) {

            // 根据代码生成主表ID 模糊查询
            if (ObjectUtil.isNotEmpty(sysCodeGenerateConfigParam.getCodeGenId())) {
                queryWrapper.eq(SysCodeGenerateConfig::getCodeGenId, sysCodeGenerateConfigParam.getCodeGenId());
            }
        }
        return this.list(queryWrapper);
    }

    @Override
    public void add(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        SysCodeGenerateConfig sysCodeGenerateConfig = new SysCodeGenerateConfig();
        BeanUtil.copyProperties(sysCodeGenerateConfigParam, sysCodeGenerateConfig);
        this.save(sysCodeGenerateConfig);
    }

    @Override
    public void addList(List<InforMationColumnsResult> inforMationColumnsResultList, SysCodeGenerate sysCodeGenerate) {
        for (InforMationColumnsResult inforMationColumnsResult : inforMationColumnsResultList) {
            SysCodeGenerateConfig sysCodeGenerateConfig = new SysCodeGenerateConfig();

            String YesOrNo = YesOrNotEnum.Y.getCode();
            if (ObjectUtil.isNotNull(inforMationColumnsResult.getColumnKey())
                    && inforMationColumnsResult.getColumnKey().equals(GenConstant.DB_TABLE_COM_KRY) ||
                    TableFilteredFieldsEnum.contains(inforMationColumnsResult.getColumnName())) {
                YesOrNo = YesOrNotEnum.N.getCode();
            }
            if (TableFilteredFieldsEnum.contains(inforMationColumnsResult.getColumnName())) {
                sysCodeGenerateConfig.setWhetherCommon(YesOrNotEnum.Y.getCode());
            } else {
                sysCodeGenerateConfig.setWhetherCommon(YesOrNotEnum.N.getCode());
            }

            sysCodeGenerateConfig.setCodeGenId(sysCodeGenerate.getId());
            sysCodeGenerateConfig.setColumnName(inforMationColumnsResult.getColumnName());
            sysCodeGenerateConfig.setColumnComment(inforMationColumnsResult.getColumnComment());
            sysCodeGenerateConfig.setJavaName(NamingConUtil.UnderlineToHump(inforMationColumnsResult.getColumnName(), sysCodeGenerate.getTablePrefix()));
            sysCodeGenerateConfig.setJavaType(JavaSqlUtil.sqlToJava(inforMationColumnsResult.getDataType()));
            sysCodeGenerateConfig.setWhetherRetract(YesOrNotEnum.N.getCode());

            sysCodeGenerateConfig.setWhetherRequired(YesOrNo);
            sysCodeGenerateConfig.setQueryWhether(YesOrNo);
            sysCodeGenerateConfig.setWhetherAddUpdate(YesOrNo);
            sysCodeGenerateConfig.setWhetherTable(YesOrNo);

            sysCodeGenerateConfig.setColumnKey(inforMationColumnsResult.getColumnKey());

            // 设置get set方法使用的名称
            String columnName = NamingConUtil.UnderlineToHump(sysCodeGenerateConfig.getColumnName(),"");
            sysCodeGenerateConfig.setColumnKeyName(columnName.substring(0,1).toUpperCase() + columnName.substring(1,columnName.length()));

            sysCodeGenerateConfig.setDataType(inforMationColumnsResult.getDataType());
            sysCodeGenerateConfig.setEffectType(JavaEffUtil.javaToEff(sysCodeGenerateConfig.getJavaType()));
            sysCodeGenerateConfig.setQueryType(QueryTypeEnum.eq.getCode());

            this.save(sysCodeGenerateConfig);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        LambdaQueryWrapper<SysCodeGenerateConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCodeGenerateConfig::getCodeGenId, sysCodeGenerateConfigParam.getCodeGenId());
        this.remove(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(List<SysCodeGenerateConfigParam> sysCodeGenerateConfigParamList) {
        for (SysCodeGenerateConfigParam sysCodeGenerateConfigParam : sysCodeGenerateConfigParamList) {
            SysCodeGenerateConfig sysCodeGenerateConfig = this.querySysCodeGenerateConfig(sysCodeGenerateConfigParam);
            BeanUtil.copyProperties(sysCodeGenerateConfigParam, sysCodeGenerateConfig);
            this.updateById(sysCodeGenerateConfig);
        }
    }

    @Override
    public SysCodeGenerateConfig detail(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        return this.querySysCodeGenerateConfig(sysCodeGenerateConfigParam);
    }

    /**
     * 获取代码生成详细配置
     *
     * @author Young-Pastor
     */
    private SysCodeGenerateConfig querySysCodeGenerateConfig(SysCodeGenerateConfigParam sysCodeGenerateConfigParam) {
        SysCodeGenerateConfig sysCodeGenerateConfig = this.getById(sysCodeGenerateConfigParam.getId());
        if (ObjectUtil.isNull(sysCodeGenerateConfig)) {
            throw new ServiceException(SysCodeGenerateConfigExceptionEnum.NOT_EXIST);
        }
        return sysCodeGenerateConfig;
    }
}
