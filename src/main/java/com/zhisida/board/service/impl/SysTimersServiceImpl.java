
package com.zhisida.board.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.board.core.exception.ServiceException;
import com.zhisida.board.core.factory.PageFactory;
import com.zhisida.board.core.pojo.page.PageResult;
import com.zhisida.board.core.timer.TimerTaskRunner;
import com.zhisida.board.entity.SysTimers;
import com.zhisida.board.enums.TimerJobStatusEnum;
import com.zhisida.board.enums.exp.SysTimersExceptionEnum;
import com.zhisida.board.mapper.SysTimersMapper;
import com.zhisida.board.param.SysTimersParam;
import com.zhisida.board.service.SysTimersService;
import com.zhisida.board.service.TimerExeService;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务 服务实现类
 *
 * @auther young-pastor
 */
@Service
public class SysTimersServiceImpl extends ServiceImpl<SysTimersMapper, SysTimers> implements SysTimersService {

    @Resource
    private TimerExeService timerExeService;

    @Override
    public PageResult<SysTimers> page(SysTimersParam sysTimersParam) {

        // 构造条件
        LambdaQueryWrapper<SysTimers> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(sysTimersParam)) {

            // 拼接查询条件-任务名称
            if (ObjectUtil.isNotEmpty(sysTimersParam.getTimerName())) {
                queryWrapper.like(SysTimers::getTimerName, sysTimersParam.getTimerName());
            }

            // 拼接查询条件-状态（字典 1运行  2停止）
            if (ObjectUtil.isNotEmpty(sysTimersParam.getJobStatus())) {
                queryWrapper.eq(SysTimers::getJobStatus, sysTimersParam.getJobStatus());
            }
        }

        // 查询分页结果
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<SysTimers> list(SysTimersParam sysTimersParam) {

        // 构造条件
        LambdaQueryWrapper<SysTimers> queryWrapper = new LambdaQueryWrapper<>();

        if (ObjectUtil.isNotNull(sysTimersParam)) {

            // 拼接查询条件-任务名称
            if (ObjectUtil.isNotEmpty(sysTimersParam.getTimerName())) {
                queryWrapper.like(SysTimers::getTimerName, sysTimersParam.getTimerName());
            }

            // 拼接查询条件-状态（字典 1运行  2停止）
            if (ObjectUtil.isNotEmpty(sysTimersParam.getJobStatus())) {
                queryWrapper.eq(SysTimers::getJobStatus, sysTimersParam.getJobStatus());
            }
        }

        return this.list(queryWrapper);
    }

    @Override
    public void add(SysTimersParam sysTimersParam) {
        checkCornExpression(sysTimersParam.getCron());

        // 将dto转为实体
        SysTimers sysTimers = new SysTimers();
        BeanUtil.copyProperties(sysTimersParam, sysTimers);

        // 设置为停止状态，点击启动时启动任务
        sysTimers.setJobStatus(TimerJobStatusEnum.STOP.getCode());

        this.save(sysTimers);
    }

    @Override
    public void delete(SysTimersParam sysTimersParam) {

        // 先停止id为参数id的定时器
        CronUtil.remove(String.valueOf(sysTimersParam.getId()));

        this.removeById(sysTimersParam.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysTimersParam sysTimersParam) {

        checkCornExpression(sysTimersParam.getCron());
        // 更新库中记录
        SysTimers oldTimer = this.querySysTimers(sysTimersParam);
        // 查看被编辑的任务的状态
        Integer jobStatus = oldTimer.getJobStatus();
        BeanUtil.copyProperties(sysTimersParam, oldTimer);
        oldTimer.setJobStatus(jobStatus);
        this.updateById(oldTimer);

        // 如果任务正在运行，则停掉这个任务，重新运行任务
        if (jobStatus.equals(TimerJobStatusEnum.RUNNING.getCode())) {
            CronUtil.remove(String.valueOf(oldTimer.getId()));
            timerExeService.startTimer(oldTimer);
        }
    }

    @Override
    public SysTimers detail(SysTimersParam sysTimersParam) {
        return this.querySysTimers(sysTimersParam);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void start(SysTimersParam sysTimersParam) {

        // 更新库中的状态
        LambdaUpdateWrapper<SysTimers> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysTimers::getJobStatus, TimerJobStatusEnum.RUNNING.getCode())
                .eq(SysTimers::getId, sysTimersParam.getId());
        this.update(wrapper);

        // 添加定时任务调度
        SysTimers sysTimers = this.querySysTimers(sysTimersParam);
        timerExeService.startTimer(sysTimers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void stop(SysTimersParam sysTimersParam) {

        // 更新库中的状态
        LambdaUpdateWrapper<SysTimers> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysTimers::getJobStatus, TimerJobStatusEnum.STOP.getCode())
                .eq(SysTimers::getId, sysTimersParam.getId());
        this.update(wrapper);

        // 关闭定时任务调度
        SysTimers sysTimers = this.querySysTimers(sysTimersParam);
        timerExeService.stopTimer(String.valueOf(sysTimers.getId()));
    }

    @Override
    public List<String> getActionClasses() {
        Map<String, TimerTaskRunner> timerTaskRunnerMap = SpringUtil.getBeansOfType(TimerTaskRunner.class);
        if (ObjectUtil.isNotEmpty(timerTaskRunnerMap)) {
            Collection<TimerTaskRunner> values = timerTaskRunnerMap.values();
            return values.stream().map(i -> i.getClass().getName()).collect(Collectors.toList());
        } else {
            return CollectionUtil.newArrayList();
        }
    }

    @Override
    public void execute(SysTimersParam sysTimersParam) {
        checkCornExpression(sysTimersParam.getCron());
        // 更新库中记录
        SysTimers oldTimer = this.querySysTimers(sysTimersParam);
        // 查看被编辑的任务的状态
        Integer jobStatus = oldTimer.getJobStatus();
        BeanUtil.copyProperties(sysTimersParam, oldTimer);
        oldTimer.setJobStatus(jobStatus);
        timerExeService.execute(oldTimer);
    }

    /**
     * 获取定时任务
     *
     * @auther young-pastor
     */
    private SysTimers querySysTimers(SysTimersParam sysTimersParam) {
        SysTimers sysTimers = this.getById(sysTimersParam.getId());
        if (ObjectUtil.isEmpty(sysTimers)) {
            throw new ServiceException(SysTimersExceptionEnum.NOT_EXISTED);
        }
        return sysTimers;
    }
    private void checkCornExpression(String expression){
        try {
            new CronSequenceGenerator(expression);
        }catch (Exception e){
            throw new ServiceException(SysTimersExceptionEnum.EXE_CORN_EXPRESSION);
        }
    }
}
