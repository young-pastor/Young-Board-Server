
package com.zhisida.board.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.util.PoiUtil;
import com.zhisida.board.entity.SysTimersLog;
import com.zhisida.board.enums.SysTimersLogExceptionEnum;
import com.zhisida.board.mapper.SysTimersLogMapper;
import com.zhisida.board.param.SysTimersLogParam;
import com.zhisida.board.service.SysTimersLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务日志service接口实现类
 *
 * @author young-pastor
 * @date 2022-06-24 17:02:32
 */
@Service
public class SysTimersLogServiceImpl extends ServiceImpl<SysTimersLogMapper, SysTimersLog> implements SysTimersLogService {

    @Override
    public PageResult<SysTimersLog> page(SysTimersLogParam sysTimersLogParam) {
        QueryWrapper<SysTimersLog> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(sysTimersLogParam)) {

            // 根据任务编号 查询
            if (ObjectUtil.isNotEmpty(sysTimersLogParam.getTimerId())) {
                queryWrapper.lambda().eq(SysTimersLog::getTimerId, sysTimersLogParam.getTimerId());
            }
            // 根据执行开始时间 查询
            if (ObjectUtil.isNotEmpty(sysTimersLogParam.getExecuteStartTime())) {
                queryWrapper.lambda().eq(SysTimersLog::getExecuteStartTime, sysTimersLogParam.getExecuteStartTime());
            }
            // 根据执行结束时间 查询
            if (ObjectUtil.isNotEmpty(sysTimersLogParam.getExecuteEndTime())) {
                queryWrapper.lambda().eq(SysTimersLog::getExecuteEndTime, sysTimersLogParam.getExecuteEndTime());
            }
            // 根据执行结果 查询
            if (ObjectUtil.isNotEmpty(sysTimersLogParam.getExecuteCode())) {
                queryWrapper.lambda().eq(SysTimersLog::getExecuteCode, sysTimersLogParam.getExecuteCode());
            }
            // 根据执行信息 查询
            if (ObjectUtil.isNotEmpty(sysTimersLogParam.getExecuteMsg())) {
                queryWrapper.lambda().eq(SysTimersLog::getExecuteMsg, sysTimersLogParam.getExecuteMsg());
            }
            // 根据执行参数 查询
            if (ObjectUtil.isNotEmpty(sysTimersLogParam.getExecuteParam())) {
                queryWrapper.lambda().eq(SysTimersLog::getExecuteParam, sysTimersLogParam.getExecuteParam());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SysTimersLog> list(SysTimersLogParam sysTimersLogParam) {
        return this.list();
    }

    @Override
    public void add(SysTimersLogParam sysTimersLogParam) {
        SysTimersLog sysTimersLog = new SysTimersLog();
        BeanUtil.copyProperties(sysTimersLogParam, sysTimersLog);
        this.save(sysTimersLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SysTimersLogParam> sysTimersLogParamList) {
        sysTimersLogParamList.forEach(sysTimersLogParam -> {
            this.removeById(sysTimersLogParam.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysTimersLogParam sysTimersLogParam) {
        SysTimersLog sysTimersLog = this.querySysTimersLog(sysTimersLogParam);
        BeanUtil.copyProperties(sysTimersLogParam, sysTimersLog);
        this.updateById(sysTimersLog);
    }

    @Override
    public SysTimersLog detail(SysTimersLogParam sysTimersLogParam) {
        return this.querySysTimersLog(sysTimersLogParam);
    }

    /**
     * 获取任务日志
     *
     * @author young-pastor
     * @date 2022-06-24 17:02:32
     */
    private SysTimersLog querySysTimersLog(SysTimersLogParam sysTimersLogParam) {
        SysTimersLog sysTimersLog = this.getById(sysTimersLogParam.getId());
        if (ObjectUtil.isNull(sysTimersLog)) {
            throw new ServiceException(SysTimersLogExceptionEnum.NOT_EXIST);
        }
        return sysTimersLog;
    }

    @Override
    public void export(SysTimersLogParam sysTimersLogParam) {
        List<SysTimersLog> list = this.list(sysTimersLogParam);
        PoiUtil.exportExcelWithStream("Young-BoardSysTimersLog.xls", SysTimersLog.class, list);
    }

}
