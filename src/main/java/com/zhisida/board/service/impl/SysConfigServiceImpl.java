
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.cache.SysConfigCache;
import com.zhisida.board.core.enums.CommonStatusEnum;
import com.zhisida.board.core.enums.YesOrNotEnum;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.entity.SysConfig;
import com.zhisida.board.enums.SysConfigExceptionEnum;
import com.zhisida.board.mapper.SysConfigMapper;
import com.zhisida.board.param.SysConfigParam;
import com.zhisida.board.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 系统参数配置service接口实现类
 *
 * @author Young-Pastor
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
    @Resource
    SysConfigCache sysConfigCache;

    @Override
    public PageResult<SysConfig> page(SysConfigParam sysConfigParam) {

        //构造查询条件
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(sysConfigParam)) {
            //如果名称不为空，则带上名称搜素搜条件
            if (ObjectUtil.isNotEmpty(sysConfigParam.getName())) {
                queryWrapper.like(SysConfig::getName, sysConfigParam.getName());
            }
            //如果常量编码不为空，则带上常量编码搜素搜条件
            if (ObjectUtil.isNotEmpty(sysConfigParam.getCode())) {
                queryWrapper.like(SysConfig::getCode, sysConfigParam.getCode());
            }
            //如果分类编码不为空，则带上分类编码
            if (ObjectUtil.isNotEmpty(sysConfigParam.getGroupCode())) {
                queryWrapper.eq(SysConfig::getGroupCode, sysConfigParam.getGroupCode());
            }
        }

        //查询未删除的
        queryWrapper.ne(SysConfig::getStatus, CommonStatusEnum.DELETED.getCode());

        //按类型升序排列，同类型的排在一起
        queryWrapper.orderByDesc(SysConfig::getGroupCode);

        //查询分页结果
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SysConfig> list(SysConfigParam sysConfigParam) {

        //构造条件
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();

        //查询未删除的
        wrapper.ne(SysConfig::getStatus, CommonStatusEnum.DELETED.getCode());

        return this.list(wrapper);
    }

    @Override
    public SysConfig detail(SysConfigParam sysConfigParam) {
        return this.querySysConfig(sysConfigParam);
    }

    @Override
    public void add(SysConfigParam sysConfigParam) {

        //1.校验参数，是否有重复的名称和编码，不排除当前记录
        checkRepeatParam(sysConfigParam, false);

        //2.构造实体
        SysConfig sysConfig = new SysConfig();
        BeanUtil.copyProperties(sysConfigParam, sysConfig);
        sysConfig.setStatus(CommonStatusEnum.ENABLE.getCode());

        //3.保存到库中
        this.save(sysConfig);

        //4.添加对应context
        sysConfigCache.remove(sysConfigParam.getCode());
    }

    @Override
    public void delete(SysConfigParam sysConfigParam) {

        //1.根据id获取常量
        SysConfig sysConfig = this.querySysConfig(sysConfigParam);

        //2.不能删除系统参数
        if (YesOrNotEnum.Y.getCode().equals(sysConfig.getSysFlag())) {
            throw new ServiceException(SysConfigExceptionEnum.CONFIG_SYS_CAN_NOT_DELETE);
        }

        //3.设置状态为已删除
        sysConfig.setStatus(CommonStatusEnum.DELETED.getCode());
        this.updateById(sysConfig);

        //4.删除对应context
        sysConfigCache.remove(sysConfigParam.getCode());
    }

    @Override
    public void edit(SysConfigParam sysConfigParam) {

        //1.根据id获取常量信息
        SysConfig sysConfig = this.querySysConfig(sysConfigParam);

        //2.校验参数，是否有重复的名称和编码，排除本条记录
        checkRepeatParam(sysConfigParam, true);

        //请求参数转化为实体
        BeanUtil.copyProperties(sysConfigParam, sysConfig);
        //不能修改状态，用修改状态接口修改状态
        sysConfig.setStatus(null);

        //3.更新记录
        this.updateById(sysConfig);

        //4.更新对应常量context
        sysConfigCache.remove(sysConfigParam.getCode());
    }

    /**
     * 校验参数，是否有重复的名称和编码
     *
     * @author Young-Pastor
     */
    private void checkRepeatParam(SysConfigParam sysConfigParam, boolean isExcludeSelf) {
        Long id = sysConfigParam.getId();
        String name = sysConfigParam.getName();
        String code = sysConfigParam.getCode();

        //构建带name和code的查询条件
        LambdaQueryWrapper<SysConfig> queryWrapperByName = new LambdaQueryWrapper<>();
        queryWrapperByName.eq(SysConfig::getName, name);

        LambdaQueryWrapper<SysConfig> queryWrapperByCode = new LambdaQueryWrapper<>();
        queryWrapperByCode.eq(SysConfig::getCode, code);

        //如果排除自己，则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapperByName.ne(SysConfig::getId, id);
            queryWrapperByCode.ne(SysConfig::getId, id);
        }
        int countByName = this.count(queryWrapperByName);
        int countByCode = this.count(queryWrapperByCode);

        //如果存在重复的记录，抛出异常，直接返回前端
        if (countByName >= 1) {
            throw new ServiceException(SysConfigExceptionEnum.CONFIG_NAME_REPEAT);
        }
        if (countByCode >= 1) {
            throw new ServiceException(SysConfigExceptionEnum.CONFIG_CODE_REPEAT);
        }
    }

    /**
     * 获取系统参数配置
     *
     * @author Young-Pastor
     */
    private SysConfig querySysConfig(SysConfigParam sysConfigParam) {
        SysConfig sysConfig = this.getById(sysConfigParam.getId());
        if (ObjectUtil.isEmpty(sysConfig)) {
            throw new ServiceException(SysConfigExceptionEnum.CONFIG_NOT_EXIST);
        }
        return sysConfig;
    }

}
