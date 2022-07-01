
package com.zhisida.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisida.core.factory.PageFactory;
import com.zhisida.core.pojo.page.PageResult;
import com.zhisida.core.util.PoiUtil;
import org.springframework.stereotype.Service;
import com.zhisida.system.entity.SysOpLog;
import com.zhisida.system.mapper.SysOpLogMapper;
import com.zhisida.system.param.SysOpLogParam;
import com.zhisida.system.service.SysOpLogService;

import java.util.List;

/**
 * 系统操作日志service接口实现类
 *
 * @author Young-Pastor
 */
@Service
public class SysOpLogServiceImpl extends ServiceImpl<SysOpLogMapper, SysOpLog> implements SysOpLogService {

    @Override
    public PageResult<SysOpLog> page(SysOpLogParam sysOpLogParam) {
        LambdaQueryWrapper<SysOpLog> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(sysOpLogParam)) {
            //根据名称模糊查询
            if (ObjectUtil.isNotEmpty(sysOpLogParam.getName())) {
                queryWrapper.like(SysOpLog::getName, sysOpLogParam.getName());
            }
            //根据操作类型查询
            if (ObjectUtil.isNotEmpty(sysOpLogParam.getOpType())) {
                queryWrapper.eq(SysOpLog::getOpType, sysOpLogParam.getOpType());
            }
            //根据是否成功查询
            if (ObjectUtil.isNotEmpty(sysOpLogParam.getSuccess())) {
                queryWrapper.eq(SysOpLog::getSuccess, sysOpLogParam.getSuccess());
            }
            // 根据时间范围查询
            if (ObjectUtil.isAllNotEmpty(sysOpLogParam.getSearchBeginTime(), sysOpLogParam.getSearchEndTime())) {
                queryWrapper.apply("date_format (op_time,'%Y-%m-%d') >= date_format('" + sysOpLogParam.getSearchBeginTime() + "','%Y-%m-%d')")
                        .apply("date_format (op_time,'%Y-%m-%d') <= date_format('" + sysOpLogParam.getSearchEndTime() + "','%Y-%m-%d')");
            }
        }
        //根据操作时间倒序排列
        queryWrapper.orderByDesc(SysOpLog::getOpTime);
        Page<SysOpLog> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return new PageResult<>(page);
    }

    @Override
    public void delete() {
        this.remove(new QueryWrapper<>());
    }

    @Override
    public void export(SysOpLogParam sysOpLogParam) {
        List<SysOpLog> list = this.list();
        PoiUtil.exportExcelWithStream("SysOpLog.xls", SysOpLog.class, list);
    }
}
